# ImgAnvil
CommandLine Image Patcher

Feature
----
1. **trim** Removing metadata
2. **nine** Converting to 9-Patched PNG with command parameters

Usage
----
```
Usage: ImgAnvil [options] [command] [command options]
  Commands:
    trim      
      Usage: trim [options]
        Options:
        * -in
             In Files
          -nodelete
             No Delete In Files
             Default: false
          -out
             Out Files

    nine      Generate 9-Patched png
      Usage: nine [options]
        Options:
          -content
             Content Padding : <left top right bottom>
        * -in
             In Files
          -inner
             Inner Stretchable Area : <left top right bottom>
          -nodelete
             No Delete In Files
             Default: false
          -out
             Out Files
          -outer
             Outer Stretchable Area : <left top right bottom>
```
  
License
----
```
The MIT License (MIT)

Copyright (c) 2015 halmakey

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
