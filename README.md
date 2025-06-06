# Java 21 Features Workshop

This repository covers the new (finalized) features of Java 21 coming from Java 17. Also, this repository serves as a workshop containing examples and exercises for each new feature.
The different exercises can be found in src/main/java/de.max.education/.
- [Java 21 Features Workshop](#java-21-features-workshop)
  - [Overview of new Features](#overview-of-new-features)
  - [Default charset UTF-8 (JEP 400)](#default-charset-utf-8-jep-400)
  - [Simple Webservers (JEP 408)](#simple-webservers-jep-408)
  - [Record Patterns (JEP 440)](#record-patterns-jep-440)
  - [Pattern Matching for switch (JEP 441)](#pattern-matching-for-switch-jep-441)
  - [Virtual Threads (JEP 444)](#virtual-threads-jep-444)
    - [Scheduling virtual threads](#scheduling-virtual-threads)
    - [Updates of java.lang.Thread and java.util.concurrent.Executors](#updates-of-javalangthread-and-javautilconcurrentexecutors)
  - [Sequenced Collections (JEP 431)](#sequenced-collections-jep-431)
  - [Key Encapsulation Mechanism (JEP 452)](#key-encapsulation-mechanism-jep-452)

## Overview of new Features

- UTF-8 by Default (JEP 400)

    This doesn't require any exercise, just be aware that Java APIs will always assume that the default charset is UTF-8 if not specified otherwise. This was done in order to avoid corrupted reading of a file that has been written with a different default charset (for example opening a file that was written in Japanese with UTF-8 with Windows in US-English)

- Simple Webservers (JEP 408)

    A simple webserver for serving static files has been added in Java 18. Because of its simplicity it is supposed to be used for prototyping, testing purposes and ad-hoc coding. Simple webservers can be started through a new command line tool or the Java API. This repository contains a simple exercise for starting the fileserver through the Java API as well as adding (custom) handlers.  

- Record Patterns (JEP 440)

    With record patterns, not only casting to a specific type can be done, but now also components of a record can be accessed directly within the instanceof operator instead of having to make use of accessor methods "manually".

- Pattern Matching for switch (JEP 441)

    This feature builds on the extension of instanceof operators that allows *pattern matching* by taking a *type pattern* which allows to the insteanceof-and-cast idiom to be simplified (JEP 394). With this feature, pattern matching (and therefore casting) can be done within switch cases.

- Virtual Threads (JEP 444)

    Instead of running each Java thread as OS thread with a wrapper around it (called a *platform thread*), Java now can map a large number of *virtual* threads to a small number of OS threads. If a virtual thread calls a blocking I/O operation it is suspended until it can be resumed later, so that the virtual thread consumes an OS thread only while it performs calculations on the CPU. Adopting virtual threads can be done with minimal change.

- Sequenced Collections (JEP 431)

    This change introduces new interfaces in order to represent collections and maps with a defined encounter order. Therefore, each collection has a well-defined first element, second element and so on. Also, the interfaces provide uniform APIs so that also the last elements can be accessed and elements can be processed in reverse order.

- Key Encapsulation Mechanism (JEP 452)

    JEP 452 introduces an API for key encapsulation mechanism (KEM), which is an encryption technique for encrypting and securing symmetric key by making use of public key cryptography.

## Default charset UTF-8 (JEP 400)

The default charset for the Java APIs has been set to UTF-8. This has been done in order to have a common denominator and therefore to avoid corrupted reading when reading the same file in different regions. While no further explanation is required, it's good to keep that change in mind.

## Simple Webservers (JEP 408)

Simple Web Servers are minimal HTTP servers for serving a single directory. Based on the com.sun.net.httpserver package, simple web servers provide an API to simplify server creation. A simple web server can be started through a command line tool:

```console
jwebserver -p 9000 -d "/mydir"
```

This creates a local webserver listening on port 9000 that serves the content of the directory *mydir*.

Additionally, a simple file server can be created through the Java API:

```java
HttpServer server = SimpleFileServer.createFileServer(InetSocketAddress, Path, OutputLevel);
server.start();
```

The *InetSocketAddress* contains the port of the server to be created, the *Path* the directory that is going to be served and the *OutputLevel* sets the verbosity of the servers output.
After starting, the server can be accessed through the browser on **localhost:{PORT}**. For a directory, all its contents and subdirectories can be accessed. Requesting a dir shows its contents, requesting a file serves it.  
Additionally, new handlers can be added that serve additional files/directories under a specified path:

```java
HttpServer server = SimpleFileServer.createFileServer(InetSocketAddress, Path, OutputLevel);
var handler = SimpleFileServer.createFileHandler(Path);
server.createContext("/newPath", handler);
server.start();
```

The server then additionally serves the directory specified in the new filehandler und the path */newPath*.

> **_NOTE:_** Note that the simple webserver is not meant for production use but for simple uses like prototyping, testing purposes and ad-hoc coding. For more complex uses it is recommended to use servers like Tomcat or Jetty (which are available through spring-boot-starter-web for example).

## Record Patterns (JEP 440)

In addition to JEP 394, JEP 440 adds the capability the directly extract the fields of a record to variables through the instanceof operator.

> **_Recap:_** The change in JEP 394 allows an object to be directly cast into the desired type and assigned to a variable (which also applies to pattern matching for switch). See the example below:

```java
// Prior to Java 16
if (obj instanceof String) {
    String s = (String)obj;
    ... use s ...
}

// As of Java 16
if (obj instanceof String s) {
    ... use s ...
}
```

Now, when using records, its components can be extracted directly (which also applies to pattern matching for switch). See the example below:

```java
record vector(double x, double y){}

static double length(Object o){
    if (o instanceof vector(double x, double y)){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    return 0;
}
```

## Pattern Matching for switch (JEP 441)

Pattern matching for switch adds upon the pattern matching added in Java 16 (JEP 394). With pattern matching for switch you can do the instanceof operation followed by an object cast and allocation in a simpler, more convenient expression.

With JEP 441, the operations for pattern matching (JEP 394) and record patterns (JEP 440) can be done in switch statements:

```java
// Prior to Java 21 making use of JEP 394
static String formatter(Object obj) {
    String formatted = "unknown";
    if (obj instanceof Integer i) {
        formatted = String.format("int %d", i);
    } else if (obj instanceof Long l) {
        formatted = String.format("long %d", l);
    } else if (obj instanceof Double d) {
        formatted = String.format("double %f", d);
    } else if (obj instanceof String s) {
        formatted = String.format("String %s", s);
    } else if (obj instanceof customer c) {
        formatted = String.format("Customer %s", c.firstName, c.lastName);
    }
    return formatted;
}

// As of Java 21
static String formatterPatternSwitch(Object obj) {
    return switch (obj) {
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        case customer(String first, String last) -> String.format("Customer: %s %s", first, last);
        default        -> obj.toString();
    };
}
```

> **_NOTE:_** Be aware that a case might never be reached if a case checking for a supertype comes before. For example, if you check if its of type Number before checking if it's an Integer will never reach the second case. This is called dominance of case labels, where the Number case label dominates the Integer case label.

All of this suggests a simple, predictable, and readable ordering of case labels in which the constant case labels should appear before the guarded pattern case labels, and those should appear before the unguarded pattern case labels:

```java
// As of Java 21
Integer i = ...
switch (i) {
    case -1, 1 -> ...                   // Special cases
    case Integer j when j > 0 -> ...    // Positive integer cases
    case Integer j -> ...               // All the remaining integers
}
```

Exhaustiveness and sealed classes

If the type of the selector expression is a sealed class (JEP 409) then the type coverage check can take into account the permits clause of the sealed class to determine whether a switch block is exhaustive. This can sometimes remove the need for a default clause, which as argued above is good practice. Consider the following example of a sealed interface S with three permitted subclasses A, B, and C:

```java
// As of Java 21
sealed interface S permits A, B, C {}
final class A implements S {}
final class B implements S {}
record C(int i) implements S {}    // Implicitly final

static int testSealedExhaustive(S s) {
    return switch (s) {
        case A a -> 1;
        case B b -> 2;
        case C c -> 3;
    };
}
```

## Virtual Threads (JEP 444)

Before JEP 444, every instance of java.lang.Thread in the JDK was a platform thread. A platform thread runs Java code on an underlying OS thread and captures the OS thread for the code's entire lifetime. The number of platform threads is limited to the number of OS threads. The problem is, that creating too many threads causes an OOM exception (since it it is limited to the number of OS threads) like:

```console
Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
```

A virtual thread is an instance of java.lang.Thread that runs Java code on an underlying OS thread but does not capture the OS thread for the code's entire lifetime. This means that many virtual threads can run their Java code on the same OS thread, effectively sharing it. While a platform thread monopolizes a precious OS thread, a virtual thread does not. The number of virtual threads can be much larger than the number of OS threads.

Virtual threads are a lightweight implementation of threads that is provided by the JDK rather than the OS. They are a form of user-mode threads, which have been successful in other multithreaded languages (e.g., goroutines in Go and processes in Erlang).
While each platform thread is implemented as wrappers for OS threads (1:1 scheduling), Virtual threads employ M:N scheduling, where a large number (M) of virtual threads is scheduled to run on a smaller number (N) of OS threads.

Virtual threads can significantly improve application throughput when

- The number of concurrent tasks is high (more than a few thousand), and
- The workload is not CPU-bound, since having many more threads than processor cores cannot improve throughput in that case.

Java debuggers can step through virtual threads, show call stacks, and inspect variables in stack frames. JDK Flight Recorder (JFR), which is the JDK's low-overhead profiling and monitoring mechanism, can associate events from application code (such as object allocation and I/O operations) with the correct virtual thread. These tools cannot do these things for applications written in the asynchronous style. In that style tasks are not related to threads, so debuggers cannot display or manipulate the state of a task, and profilers cannot tell how much time a task spends waiting for I/O.

Virtual threads are cheap and plentiful, and thus **should never be pooled**: A new virtual thread should be created for every application task. Most virtual threads will thus be short-lived and have shallow call stacks, performing as little as a single HTTP client call or a single JDBC query. Platform threads, by contrast, are heavyweight and expensive, and thus often must be pooled. They tend to be long-lived, have deep call stacks, and be shared among many tasks.

In summary, virtual threads preserve the reliable thread-per-request style that is harmonious with the design of the Java Platform while utilizing the available hardware optimally. Using virtual threads does not require learning new concepts, though it may require unlearning habits developed to cope with today's high cost of threads. Virtual threads will not only help application developers — they will also help framework designers provide easy-to-use APIs that are compatible with the platform's design without compromising on scalability.

### Scheduling virtual threads

To do useful work a thread needs to be scheduled, that is, assigned for execution on a processor core. For platform threads, which are implemented as OS threads, the JDK relies on the scheduler in the OS. For virtual threads, by contrast, the JDK has its own scheduler. Rather than assigning virtual threads to processors directly, the JDK's scheduler assigns virtual threads to platform threads (this is the M:N scheduling of virtual threads mentioned earlier). The platform threads are then scheduled by the OS as usual.

### Updates of java.lang.Thread and java.util.concurrent.Executors

The java.lang.Thread API was updated as follows:

- ```Thread.Builder```, ```Thread.ofVirtual()```, and ```Thread.ofPlatform()``` are new APIs to create virtual and platform threads. For example,
- ```Thread thread = Thread.ofVirtual().name("duke").unstarted(runnable);```
creates a new unstarted virtual thread named "duke".
- ```Thread.startVirtualThread(Runnable)``` is a convenient way to create and then start a virtual thread.
- A Thread.Builder can create either a thread or a ThreadFactory, which can then create multiple threads with identical properties.
- ```Thread.isVirtual()``` tests whether a thread is a virtual thread.
- ```Thread.getAllStackTraces()``` now returns a map of all platform threads rather than all threads.

When using Executors, developers can choose whether to use virtual threads or platform threads. A virtual thread executor can simply be created through ```Executors.newVirtualThreadPerTaskExecutor();``` Here is an example program that creates a large number of virtual threads. The program first obtains an ExecutorService that will create a new virtual thread for each submitted task. It then submits 10,000 tasks and waits for all of them to complete:

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i -> {
        executor.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            return i;
        });
    });
}  // executor.close() is called implicitly, and waits
```

## Sequenced Collections (JEP 431)

JEP 431 introduces new interfaces that represent collections with a defined encounter order. Each such collection has a well-defined first element, second element, and so forth, up to the last element. It also provides uniform APIs for accessing its first and last elements, and for processing its elements in reverse order.
While streamlining different types to have the same method names (e.g. Deque and SortedSet now both have the method ```getFirst()```), methods providing additional functionality have been added also (e.g. getting the last element of a LinkedHashSet).

Here is an overview of the new interfaces:

```java
interface SequencedCollection<E> extends Collection<E> {
    // new method
    SequencedCollection<E> reversed();
    // methods promoted from Deque
    void addFirst(E);
    void addLast(E);
    E getFirst();
    E getLast();
    E removeFirst();
    E removeLast();
}

interface SequencedSet<E> extends Set<E>, SequencedCollection<E> {
    SequencedSet<E> reversed();    // covariant override
}

interface SequencedMap<K,V> extends Map<K,V> {
    // new methods
    SequencedMap<K,V> reversed();
    SequencedSet<K> sequencedKeySet();
    SequencedCollection<V> sequencedValues();
    SequencedSet<Entry<K,V>> sequencedEntrySet();
    V putFirst(K, V);
    V putLast(K, V);
    // methods promoted from NavigableMap
    Entry<K, V> firstEntry();
    Entry<K, V> lastEntry();
    Entry<K, V> pollFirstEntry();
    Entry<K, V> pollLastEntry();
}
```

The hierarchy of the interfaces, including the new ones can be seen here:
![Interface hierarchy](./images/SequencedCollectionDiagram20220216.png "Interface hierarchy")

## Key Encapsulation Mechanism (JEP 452)

JEP 452 adds an API for key encapsulation mechanisms (KEMs), which is an encryption technique for securing symmetric key using public key cryptography. This enables applications to use KEM algorithms like RSA-KEM and Elliptic Curve Integrated Encryption Scheme and includes an implementation of the Diffie-Hellman KEM.
KEMs are candidates for the next generation of standard public key cryptography algorithms, also providing proper defense against quantum attacks.
