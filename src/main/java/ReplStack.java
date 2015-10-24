import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Created by kevinzhong on 25/10/15.
 */
public class ReplStack<T> extends ReceiverAdapter {
    JChannel channel;
    Stack<T> stack;
    private static final String CLUSTER_NAME = "StackCluster";

    public ReplStack() throws Exception {
        stack = new Stack<T>();
        channel=new JChannel(); // use the default config, udp.xml
        channel.setReceiver(this);
        channel.connect(CLUSTER_NAME);
        channel.getState(null, 0);
    }

    public void push(T t) throws Exception {
        SerializedObj<T> obj = new SerializedObj<T>(SerializedObj.STACK_PUSH);
        obj.setValue(t);
        Message msg=new Message(null, null, obj);
        channel.send(msg);
    };

    public T pop() throws Exception {
        T feedback = top();
        SerializedObj<T> obj = new SerializedObj<T>(SerializedObj.STACK_POP);
        Message msg=new Message(null, null, obj);
        channel.send(msg);
        return feedback;
    };

    public T top(){
        T feedback;
        synchronized (stack){
            if (stack.empty()){
                return null;
            }
            else{
                feedback = stack.peek();
                return  feedback;
            }
        }
    };

    public void exit(){
        channel.close();
    }

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }


    public void receive(Message msg) {
        if ( msg.getObject() instanceof SerializedObj){
            SerializedObj<T> obj = ((SerializedObj) msg.getObject());
            if (obj.getType() == SerializedObj.STACK_PUSH){
                synchronized (stack){
                    stack.push(obj.getValue());
                }
            }
            else if (obj.getType() == SerializedObj.STACK_POP){
                synchronized (stack){
                    if (stack.empty()){
                        System.out.println("Stack kosong!!");
                    }
                    else{
                        stack.pop();
                    }
                }
            }
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(stack) {
            Util.objectToStream(stack, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        List<T> list;
        list = (List<T>) Util.objectFromStream(new DataInputStream(input));
        synchronized(stack) {
            stack.clear();
            stack.addAll(list);
        }
        System.out.println(list.size() + " elemen in stack history):");
        for(T str: list)
            System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        ReplStack<String> repl_stack = new ReplStack<String>();

        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                String[] inputs = line.split("\\s+");
                if(line.startsWith("quit") || line.startsWith("exit")){
                    break;
                }
                else if (line.startsWith("push")){
                    if (inputs.length == 2){
                        repl_stack.push(inputs[1]);
                    }
                    else{
                        System.out.println("Usage: push <value_object>");
                    }
                }
                else if (line.startsWith("pop")){
                    System.out.println(repl_stack.pop());
                }
                else if (line.startsWith("top")){
                    System.out.println(repl_stack.top());
                }
                else{
                    System.out.println("Invalid command!");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        repl_stack.exit();
    }

}
