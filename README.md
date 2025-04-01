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

- Virtual Threads

    Text

- Sequenced Collections (JEP 431)

    text

- Key Encapsulation Mechanism (JEP 452)
