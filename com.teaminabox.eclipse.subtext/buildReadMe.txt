Unfortunately, because we're using ANTLR to generate some code, the build for this project is not a single click.

You need to open a terminal, change directory to the project's root and intone 'ant -f antlrbuild.xml'. When that is finished,
you can generate the plugin jar or zip as usual.

You only need to do the extra step if the grammar has changed (the stuff in src/antlr).