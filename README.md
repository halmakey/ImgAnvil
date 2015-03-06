# ImgAnvil
CommandLine Image Patcher

Feature
----
1. **nine** Converting to 9-Patched PNG with command parameters
2. **trim** Removing metadata

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
