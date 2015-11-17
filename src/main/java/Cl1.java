

//import akka.actor.*;

/**
 *
 */

public class Cl1{

}


//public class Cl1 {
//    public static void main(String[] a) {
//        ActorSystem system = ActorSystem.create("system");
//        final ActorRef kernel = system.actorOf(Props.create(Kernel.class), "kernel");
//
//
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            String f = sc.nextLine();
//            if (f.equals("exit")) {
//                break;
//            }
//            kernel.tell(f, ActorRef.noSender());
//        }
//        system.terminate();
//    }
//}
//
//class Kernel extends UntypedActor {
//
//    private TreeMap<Integer, Pair<Integer, Boolean>> jobs = new TreeMap<Integer, Pair<Integer, Boolean>>();
//    private int job_id_counter = 0;
//
//    @Override
//    public void onReceive(Object arg0) throws Exception {
////        System.out.println("Kernel Thread: "+Thread.currentThread().toString());
//        if (arg0 instanceof String) {
//            int i = Integer.valueOf((String) arg0);
//            for (int j = 2; j < i; j++)
//                getContext().actorOf(Props.create(PrimeWorker.class)).tell(new Job(job_id_counter, i, j, j + 1), getSelf());
//            jobs.put(job_id_counter, new Pair<>(i - 2, true));
//            job_id_counter++;
//            return;
//        }
//
//        if (arg0 instanceof JobResult) {
//            JobResult jr = (JobResult) arg0;
//            Pair<Integer, Boolean> task = jobs.get(jr.jobID);
//            if (!jr.isPrime) task.second = false;
//            task.first--;
//            if (task.first < 1) {
//                System.out.println("Число " + jr.number + (task.second ? " простое" : " составное"));
//                jobs.remove(jr.jobID);
//            }
//            getSender().tell(PoisonPill.getInstance(), getSelf()); //Актор сделал свою работу, отправляем ему команду уничтожения
//        }
//        unhandled(arg0);
//    }
//}
//
//class PrimeWorker extends UntypedActor {
//
//    @Override
//    public void onReceive(Object arg0) throws Exception {
////        System.out.println("Worker Thread: "+Thread.currentThread().toString());
//        if (arg0 instanceof Job) {
//            Job task = (Job) arg0;
//            for (int i = task.from; i < task.to; i++)
//                if (task.number % i == 0) { //Число составное
//                    getSender().tell(new JobResult(task.jobID, false,task.number), getSelf());
//                    return;
//                }
//            getSender().tell(new JobResult(task.jobID, true,task.number), getSelf());
//
//        }
//        unhandled(arg0);
//    }
//}
//
//class Job {
//    public final int jobID; //Идентификатор задачи, используется для аггрегации результатов
//    public final int number; //Исследуемое число
//    public final int from; //Нижняя граница диапазона перебора
//    public final int to; //Верхняя граница диапазона перебора
//
//    public Job(int jobID, int number, int from, int to) {
//        this.jobID = jobID;
//        this.number = number;
//        this.from = from;
//        this.to = to;
//    }
//}
//
//class JobResult implements Serializable {
//    private static final long serialVersionUID = -1788069759380966076L;
//
//    public final int jobID;
//    public final boolean isPrime;
//    public final int number;
//
//    public JobResult(int jobID, boolean isPrime, int number) {
//        this.jobID = jobID;
//        this.isPrime = isPrime;
//        this.number = number;
//    }
//}
//
//class Pair<A, B> {
//    A first;
//    B second;
//
//    public Pair(A first, B second) {
//        this.first = first;
//        this.second = second;
//    }
//}