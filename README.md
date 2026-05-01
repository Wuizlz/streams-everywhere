# Streams Everywhere

This project matches the proposal for the CS 33600 honors stacked component.
The core idea is to compare two kinds of stream-based communication in Java:

- Local inter-process communication with `ProcessBuilder`
- HTTP request/response streams with `HttpClient`

The final project is supposed to produce both runnable demos and a research
report that explains what the demos show.

## What To Build First

Start with the demos before the paper.

1. Build the IPC demo so you can observe stdout, stderr, redirection, and exit
   codes in a controlled environment.
2. Build the HTTP demo so you can inspect status codes, headers, and body
   decoding from real responses.
3. Record evidence from those runs for screenshots and notes.
4. Write the report after the demos are producing results you can analyze.

That order is faster because the report will need concrete observations, not
just theory.

## Project Layout

- `ipc-demo/`
  Starter code for local process-stream experiments with `ProcessBuilder`
- `http-demo/`
  Starter code for HTTP stream experiments with `HttpClient`
- `report/`
  Outline for the final paper
- `evidence/`
  Screenshots, terminal captures, sample outputs, and notes

## Suggested Milestones

### Milestone 1: IPC Baseline

- Run a child process with `inheritIO()`
- Run a child process with captured stdout/stderr
- Run a two-process pipeline
- Redirect output to a file
- Compare exit codes and output behavior

### Milestone 2: HTTP Baseline

- Send a GET request
- Print the status code
- Print key headers
- Decode the body using the charset from `Content-Type`
- Compare different endpoints such as `200`, `302`, `401`, and `403`

### Milestone 3: Evidence Collection

- Save terminal output
- Capture screenshots
- Keep notes on buffering, errors, redirect behavior, and decoding differences

### Milestone 4: Report Draft

- Use the outline in [report/report-outline.md](/Users/wuzi/Desktop/java%20workspace/Networking-Programming/streams-everywhere/report/report-outline.md)
- Convert demo observations into comparisons and conclusions
- Add at least 8 academic or industry sources

## Compile And Run

From the project root:

```bash
cd "/Users/wuzi/Desktop/java workspace/Networking-Programming/streams-everywhere"
```

Compile the IPC demo:

```bash
javac ipc-demo/*.java
```

Run the IPC demo:

```bash
java -cp ipc-demo ProcessBuilderIpcDemo
```

Compile the HTTP demo:

```bash
javac http-demo/*.java
```

Run the HTTP demo:

```bash
java -cp http-demo FetchURLDemo https://example.com
```

## Immediate Next Step

If you want the cleanest start, do this next:

1. Compile both demo folders.
2. Run the IPC demo and save its output.
3. Run the HTTP demo against a simple URL.
4. Start filling `evidence/` and `report/report-outline.md` as you learn things.
