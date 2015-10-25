import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kevinzhong on 25/10/15.
 */
public class ReplSet<T> extends ReceiverAdapter {
    JChannel channel;
    HashSet<T> set;
    private static final String CLUSTER_NAME = "SetCluster";

    public ReplSet() throws Exception {
        set = new HashSet<T>();
        channel=new JChannel(); // use the default config, udp.xml
        channel.setReceiver(this);
        channel.connect(CLUSTER_NAME);
        channel.getState(null, 10000);
    }

    /** mengembalikan true jika obj ditambahkan dan false jika obj telah ada pada set */
    public boolean add(T obj) throws Exception {
        SerializedObj<T> serialized_obj = new SerializedObj<T>(SerializedObj.SET_ADD);
        serialized_obj.setValue(obj);
        Message msg=new Message(null, null, serialized_obj);
        if (contains(obj)){
            return false;
        }
        else{
            channel.send(msg);
            return true;
        }
    }

    /** mengembalikan true jika obj ada pada set */
    public boolean contains(T obj){
        synchronized (set){
            return set.contains(obj);
        }
    }

    /** mengembalikan true jika obj ada pada set, dan kemudian obj dihapus dari set.
     * Mengembalikan false jika obj tidak ada pada set */
    public boolean remove(T obj) throws Exception {
        SerializedObj<T> serialized_obj = new SerializedObj<T>(SerializedObj.SET_REMOVE);
        serialized_obj.setValue(obj);
        Message msg=new Message(null, null, serialized_obj);
        if (contains(obj)){
            channel.send(msg);
            return true;
        }
        else{
            return false;
        }
    }

    public void exit(){
        channel.close();
    }

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        if ( msg.getObject() instanceof SerializedObj){
            SerializedObj<T> obj = ((SerializedObj) msg.getObject());
            if (obj.getType() == SerializedObj.SET_ADD){
                synchronized (set){
                    set.add(obj.getValue());
                }
            }
            else if (obj.getType() == SerializedObj.SET_REMOVE){
                synchronized (set){
                    set.remove(obj.getValue());
                }
            }
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(set) {
            Util.objectToStream(set, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        HashSet<T> another_set;
        another_set = (HashSet<T>) Util.objectFromStream(new DataInputStream(input));
        synchronized(set) {
            set.clear();
            set.addAll(another_set);
        }
        System.out.println(another_set.size() + " elemen in stack history):");
        for(T str: another_set)
            System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        ReplSet<String> repl_set = new ReplSet<String>();

        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                String[] inputs = line.split("\\s+");
                if(line.startsWith("quit") || line.startsWith("exit")){
                    break;
                }
                else if (line.startsWith("add")){
                    if (inputs.length == 2){
                        if (repl_set.add(inputs[1])){
                            System.out.println("Elemen " + inputs[1] + " berhasil ditambahkan");
                        }
                        else{
                            System.out.println("Elemen " + inputs[1] + " sudah ada!");
                        }
                    }
                    else{
                        System.out.println("Usage: add <value_object>");
                    }
                }
                else if (line.startsWith("remove")){
                    if (inputs.length == 2){
                        if (repl_set.remove(inputs[1])){
                            System.out.println("Elemen " + inputs[1] + " berhasil dikeluarkan");
                        }
                        else{
                            System.out.println("Elemen " + inputs[1] + " tidak terdapat pada set!");
                        }
                    }
                    else{
                        System.out.println("Usage: remove <value_object>");
                    }
                }
                else if (line.startsWith("contains")){
                    if (inputs.length == 2){
                        if (repl_set.contains(inputs[1])){
                            System.out.println("Elemen " + inputs[1] + " terdapat pada set!");
                        }
                        else{
                            System.out.println("Elemen " + inputs[1] + " tidak terdapat pada set!");
                        }
                    }
                    else{
                        System.out.println("Usage: contains <value_object>");
                    }
                }
                else{
                    System.out.println("Invalid command!");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        repl_set.exit();
    }
}
