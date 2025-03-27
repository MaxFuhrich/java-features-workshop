# Java 21 Features Workshop

This repository covers the new (finalized) features of Java 21 coming from Java 17. Also, this repository serves as a workshop containing examples and exercies for each new feature. 

## Overview of new Features

- Default charset is always UTF-8 instead of being environment dependent (JEP 400)

    This doesn't require any exercise, just be aware that Java APIs will always assume that the default charset is UTF-8 if not specified otherwise. This was done in order to avoid corrupted reading of a file that has been written with a different default charset (for example opening a file that was written in Japanese with UTF-8 with Windows in US-English)

- Simple webservers (JEP 408)