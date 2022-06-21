//package messages;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class SentFacade {
//    
//    private static List<SentDAO> sent;
//
//    /**
//    * Constructor
//    */
//    public SentFacade() {
//        sent = new ArrayList<>();
//        sent.add(new SentDAO());
//    }
//    public static void home(){
//        makeActions(sent, SentDAO.Action.GO_HOME);
//    }
//    public static void back(){
//        makeActions(sent, SentDAO.Action.BACK);
//    }
//    public static void show_sent(){
//        makeActions(sent, SentDAO.Action.SHOW_SENT);
//    }
//    public static void send_mail() {
//        makeActions(sent, SentDAO.Action.GO_TO_SEND);
//    }
//    public static void delete_sent_mail() {
//        makeActions(sent, SentDAO.Action.DELETE);
//    }
//    
//    private static void makeActions(Collection<SentDAO> sent,
//        SentDAO.Action... actions) {
//        for (SentDAO sender : sent) {
//            sender.action(actions);
//        }
//    }
//}
