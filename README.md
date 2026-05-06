# Streams Everywhere

Final honors stacked project for CS 33600: Network Programming.

The project compares two stream-based communication models in Java:

- local inter-process communication with `ProcessBuilder`
- HTTP request/response communication with `HttpClient`

The final report argues that both models move ordered data through streams, but
they differ in control, observability, metadata, and failure handling.

## Final Report

The finalized submission report is:

- `final/Streams_Everywhere_Final.pdf`

Earlier Markdown planning/draft files are kept for version history only. The
PDF above is the submission copy:

- `report/first-draft.md`
- `report/report-outline.md`

## Project Layout

- `ipc-demo/`
  Java source for the local process-stream demo.
- `http-demo/`
  Java source for the HTTP request/response demo.
- `evidence/`
  Runtime outputs and Kraft course-note PDFs used as report evidence.
- `report/`
  Working report draft and outline.
- `final/`
  Final exported report.

## Compile And Run

Requirements:

- JDK 11 or newer
- Internet access for the HTTP demo

```bash
cd streams-everywhere
```

Compile and run the IPC demo:

```bash
javac ipc-demo/*.java
java -cp ipc-demo ProcessBuilderIpcDemo
```

Compile and run the HTTP demo:

```bash
javac http-demo/*.java
java -cp http-demo FetchURLDemo https://example.com
```

Regenerate the saved runtime evidence:

```bash
javac ipc-demo/*.java
javac http-demo/*.java

java -cp ipc-demo ProcessBuilderIpcDemo > evidence/ipc-run-output.txt 2>&1
java -cp http-demo FetchURLDemo https://example.com > evidence/http-example-output.txt 2>&1
java -cp http-demo FetchURLDemo https://api.github.com > evidence/http-github-output.txt 2>&1
java -cp http-demo FetchURLDemo https://www.google.com > evidence/http-google-output.txt 2>&1
java -cp http-demo FetchURLDemo http://github.com > evidence/http-redirect-output.txt 2>&1
java -cp http-demo FetchURLDemo https://httpbin.org/basic-auth/user/passwd > evidence/http-401-output.txt 2>&1
java -cp http-demo FetchURLDemo https://httpbin.org/status/403 > evidence/http-403-output.txt 2>&1
```

## Runtime Evidence

The saved demo outputs are:

- `evidence/ipc-run-output.txt`
- `evidence/http-google-output.txt`
- `evidence/http-github-output.txt`
- `evidence/http-redirect-output.txt`
- `evidence/http-401-output.txt`
- `evidence/http-403-output.txt`
- `evidence/http-example-output.txt`
- `ipc-demo/producer-output.txt`
- `handshake.log`

These cover inherited/captured process streams, pipelines, file redirection,
exit codes, HTTP `200`, redirect, `401`, `403`, charset decoding, and an earlier
TLS/PKIX failure example.

## Source Evidence

The Kraft course-note PDFs cited in the report are kept locally:

- `evidence/HTTP - CS_33600.pdf`
- `evidence/Network clients - CS_33600.pdf`
- `evidence/ProcessBuilder - CS_33600.pdf`
- `evidence/Streams - CS_33600.pdf`

Additional source notes are tracked in:

- `evidence/vital-sources.md`
