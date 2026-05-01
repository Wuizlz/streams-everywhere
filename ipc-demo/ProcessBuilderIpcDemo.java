import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProcessBuilderIpcDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("== IPC Demo ==");
        System.out.println();

        runInheritedIoExample();
        runCapturedOutputExample();
        runPipelineExample();
        runFileRedirectionExample();
    }

    private static void runInheritedIoExample() throws Exception {
        System.out.println("== inheritIO() Example ==");

        ProcessBuilder builder = baseJavaCommand("MessageProducer", "0");
        builder.inheritIO();

        Process process = builder.start();
        int exitCode = process.waitFor();

        System.out.println("inheritIO exit code: " + exitCode);
        System.out.println();
    }

    private static void runCapturedOutputExample() throws Exception {
        System.out.println("== Captured stdout/stderr Example ==");

        ProcessBuilder builder = baseJavaCommand("MessageProducer", "3");
        Process process = builder.start();

        String stdout = readFully(process.getInputStream());
        String stderr = readFully(process.getErrorStream());
        int exitCode = process.waitFor();

        System.out.println("-- stdout --");
        System.out.print(stdout);
        System.out.println("-- stderr --");
        System.out.print(stderr);
        System.out.println("captured exit code: " + exitCode);
        System.out.println();
    }

    private static void runPipelineExample() throws Exception {
        System.out.println("== Pipeline Example ==");

        ProcessBuilder producer = baseJavaCommand("MessageProducer", "0");
        ProcessBuilder filter = baseJavaCommand("UppercaseFilter");

        List<Process> pipeline = ProcessBuilder.startPipeline(List.of(producer, filter));
        Process finalProcess = pipeline.get(pipeline.size() - 1);

        String pipelineOutput = readFully(finalProcess.getInputStream());
        int finalExitCode = finalProcess.waitFor();

        for (Process process : pipeline) {
            process.waitFor();
        }

        System.out.println("-- pipeline output --");
        System.out.print(pipelineOutput);
        System.out.println("pipeline final exit code: " + finalExitCode);
        System.out.println();
    }

    private static void runFileRedirectionExample() throws Exception {
        System.out.println("== File Redirection Example ==");

        Path outputFile = Path.of("ipc-demo", "producer-output.txt");
        Files.deleteIfExists(outputFile);

        ProcessBuilder builder = baseJavaCommand("MessageProducer", "0");
        builder.redirectOutput(outputFile.toFile());
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = builder.start();
        int exitCode = process.waitFor();

        String fileContents = Files.readString(outputFile, StandardCharsets.UTF_8);

        System.out.println("redirected file: " + outputFile);
        System.out.println("-- file contents --");
        System.out.print(fileContents);
        System.out.println("file redirection exit code: " + exitCode);
        System.out.println();
    }

    private static ProcessBuilder baseJavaCommand(String mainClass, String... args) {
        String javaHome = System.getProperty("java.home");
        String javaBinary = Path.of(javaHome, "bin", "java").toString();

        String[] command = new String[4 + args.length];
        command[0] = javaBinary;
        command[1] = "-cp";
        command[2] = "ipc-demo";
        command[3] = mainClass;

        System.arraycopy(args, 0, command, 4, args.length);
        return new ProcessBuilder(command);
    }

    private static String readFully(InputStream inputStream) throws IOException {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }

            return builder.toString();
        }
    }
}
