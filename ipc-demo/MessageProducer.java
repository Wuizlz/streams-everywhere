public class MessageProducer {
    public static void main(String[] args) {
        System.out.println("alpha");
        System.out.println("bravo");
        System.err.println("producer: sample warning on stderr");
        System.out.println("charlie");

        int exitCode = args.length > 0 ? Integer.parseInt(args[0]) : 0;
        System.exit(exitCode);
    }
}
