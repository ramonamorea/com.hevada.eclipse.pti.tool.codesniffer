Thanks for great tools from [PHPsrc](https://github.com/PHPsrc).

Sadly, it hasn't been updated for years. I have to use these great tools in my daily work, so I've decided to try tailoring.


## Installation

You'll have to install the official package from [PHPsrc update site](http://www.phpsrc.org/eclipse/pti/)

```text
http://www.phpsrc.org/eclipse/pti/
```

Then use this repository to overwrite the original plugin.

If you're feeling lazy :smirk:, just copy from the `dist` directory, to eclipse' plugin directory. If you see a directory is inside the plugin directory, instead of just a `jar` file, you can safely unzip the jar from `dist` to the plugin directory.

You can also build the plugin by yourself. However, you'll have to do it all manually. Because there is no build script to help you building the end product. But no worry, it's not so difficult, just use the `export` function in eclipse, to export a `jar` file (not runnable jar).