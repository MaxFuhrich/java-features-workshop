# Java 21 Features Workshop

This repository covers the new (finalized) features of Java 21 coming from Java 17. Also, this repository serves as a workshop containing examples and exercies for each new feature. 

## Overview of new Features

- Default charset is always UTF-8 instead of being environment dependent (JEP 400)

    This doesn't require any exercise, just be aware that Java APIs will always assume that the default charset is UTF-8 if not specified otherwise. This was done in order to avoid corrupted reading of a file that has been written with a different default charset (for example opening a file that was written in Japanese with UTF-8 with Windows in US-English)

- Simple webservers (JEP 408)

    A simple webserver for serving static files has been added in Java 18. Because of its simplicity it is supposed to be used for prototyping, testing purposes and ad-hoc coding. Simple webservers can be started through a new command line tool or the Java API. This repository contains a simple exercise for starting the fileserver through the Java API as well as adding (custom) handlers.  

- Pattern Matching for switch (JEP 441)

    This feature builds on the extension of instanceof operators that allows *pattern matching* by taking a *type pattern* which allows to the insteanceof-and-cast idiom to be simplified (JEP 394). With this feature, pattern matching (and therefore casting) can be done within switch cases.

- Record Patterns (JEP 440)

    With record patterns, not only casting to a specific type can be done, but now also components of a record can be accessed directly within the instanceof operator instead of having to make use of accessor methods "manually"

    TODO: Klären was records sind

- Virtual Threads (JEP 444)

    Instead of running each Java thread as OS thread with a wrapper around it (called a *platform thread*), Java now can map a large number of *virtual* threads to a small number of OS threads. If a virtual thread calls a blocking I/O operation it is suspended until it can be resumed later, so that the virtual thread consumes an OS thread only while it performs calculations on the CPU. Adopting virtual threads can be done with minimal change.

- Sequenced Collections (JEP 431)

    text

- Key Encapsulation Mechanism (JEP 452)

    text

## Virtual Threads

TODO: Beispiel mit Littles Law:
The scalability of server applications is governed by Little's Law, which relates latency, concurrency, and throughput: For a given request-processing duration (i.e., latency), the number of requests an application handles at the same time (i.e., concurrency) must grow in proportion to the rate of arrival (i.e., throughput). For example, suppose an application with an average latency of 50ms achieves a throughput of 200 requests per second by processing 10 requests concurrently. In order for that application to scale to a throughput of 2000 requests per second, it will need to process 100 requests concurrently. If each request is handled in a thread for the request's duration then, for the application to keep up, the number of threads must grow as throughput grows.
λ = 200 bzw. 2000
W = 50 ms bzw 0,05s
L = 10 bzw 1000

Implications of virtual threads

Virtual threads are cheap and plentiful, and thus should never be pooled: A new virtual thread should be created for every application task. Most virtual threads will thus be short-lived and have shallow call stacks, performing as little as a single HTTP client call or a single JDBC query. Platform threads, by contrast, are heavyweight and expensive, and thus often must be pooled. They tend to be long-lived, have deep call stacks, and be shared among many tasks.

In summary, virtual threads preserve the reliable thread-per-request style that is harmonious with the design of the Java Platform while utilizing the available hardware optimally. Using virtual threads does not require learning new concepts, though it may require unlearning habits developed to cope with today's high cost of threads. Virtual threads will not only help application developers — they will also help framework designers provide easy-to-use APIs that are compatible with the platform's design without compromising on scalability.

### Description

Today, every instance of java.lang.Thread in the JDK is a platform thread. A platform thread runs Java code on an underlying OS thread and captures the OS thread for the code's entire lifetime. The number of platform threads is limited to the number of OS threads.

A virtual thread is an instance of java.lang.Thread that runs Java code on an underlying OS thread but does not capture the OS thread for the code's entire lifetime. This means that many virtual threads can run their Java code on the same OS thread, effectively sharing it. While a platform thread monopolizes a precious OS thread, a virtual thread does not. The number of virtual threads can be much larger than the number of OS threads.

Virtual threads are a lightweight implementation of threads that is provided by the JDK rather than the OS. They are a form of user-mode threads, which have been successful in other multithreaded languages (e.g., goroutines in Go and processes in Erlang). User-mode threads even featured as so-called "green threads" in early versions of Java, when OS threads were not yet mature and widespread. However, Java's green threads all shared one OS thread (M:1 scheduling) and were eventually outperformed by platform threads, implemented as wrappers for OS threads (1:1 scheduling). Virtual threads employ M:N scheduling, where a large number (M) of virtual threads is scheduled to run on a smaller number (N) of OS threads.

To put it another way, virtual threads can significantly improve application throughput when

- The number of concurrent tasks is high (more than a few thousand), and
- The workload is not CPU-bound, since having many more threads than processor cores cannot improve throughput in that case.

Java debuggers can step through virtual threads, show call stacks, and inspect variables in stack frames. JDK Flight Recorder (JFR), which is the JDK's low-overhead profiling and monitoring mechanism, can associate events from application code (such as object allocation and I/O operations) with the correct virtual thread. These tools cannot do these things for applications written in the asynchronous style. In that style tasks are not related to threads, so debuggers cannot display or manipulate the state of a task, and profilers cannot tell how much time a task spends waiting for I/O.

Scheduling virtual threads

To do useful work a thread needs to be scheduled, that is, assigned for execution on a processor core. For platform threads, which are implemented as OS threads, the JDK relies on the scheduler in the OS. For virtual threads, by contrast, the JDK has its own scheduler. Rather than assigning virtual threads to processors directly, the JDK's scheduler assigns virtual threads to platform threads (this is the M:N scheduling of virtual threads mentioned earlier). The platform threads are then scheduled by the OS as usual.

New diagnostics assist in migrating code to virtual threads and in assessing whether you should replace a particular use of synchronized with a java.util.concurrent lock:

A JDK Flight Recorder (JFR) event is emitted when a thread blocks while pinned (see JDK Flight Recorder).

The system property jdk.tracePinnedThreads triggers a stack trace when a thread blocks while pinned. Running with -Djdk.tracePinnedThreads=full prints a complete stack trace when a thread blocks while pinned, highlighting native frames and frames holding monitors. Running with -Djdk.tracePinnedThreads=short limits the output to just the problematic frames.

In a future release we may be able to remove the first limitation above, namely pinning inside synchronized. The second limitation is required for proper interaction with native code.

java.lang.Thread

We update the java.lang.Thread API as follows:

Thread.Builder, Thread.ofVirtual(), and Thread.ofPlatform() are new APIs to create virtual and platform threads. For example,

Thread thread = Thread.ofVirtual().name("duke").unstarted(runnable);
creates a new unstarted virtual thread named "duke".

Thread.startVirtualThread(Runnable) is a convenient way to create and then start a virtual thread.

A Thread.Builder can create either a thread or a ThreadFactory, which can then create multiple threads with identical properties.

Thread.isVirtual() tests whether a thread is a virtual thread.

Thread.getAllStackTraces() now returns a map of all platform threads rather than all threads.

The java.lang.Thread API is otherwise unchanged by this JEP. The constructors defined by the Thread class create platform threads, as before. There are no new public constructors.

(Three methods in Thread which throw UnsupportedOperationException for virtual threads — stop(), suspend(), and resume() — were changed in JDK 20 to throw UnsupportedOperationException for platform threads too.)

The main API differences between virtual and platforms threads are:

The public Thread constructors cannot create virtual threads.

Virtual threads are always daemon threads. The Thread.setDaemon(boolean) method cannot change a virtual thread to be a non-daemon thread.

Virtual threads have a fixed priority of Thread.NORM_PRIORITY. The Thread.setPriority(int) method has no effect on virtual threads. This limitation may be revisited in a future release.

Virtual threads are not active members of thread groups. When invoked on a virtual thread, Thread.getThreadGroup() returns a placeholder thread group with the name "VirtualThreads". The Thread.Builder API does not define a method to set the thread group of a virtual thread.

Virtual threads have no permissions when running with a SecurityManager set.

Platform threads: 1:1 (1 platform thread, 1 os thread)
Virtual thread: M:N (M virtual threads, N os threads, M can be much bigger)

Beispiel:
Anwendung die Threads startet die sleep machen. Parameter = Anzahl Threads
Erhöhen, bis es kracht. Dann wechsel auf virtual Threads.
Zeitmessung einbauen