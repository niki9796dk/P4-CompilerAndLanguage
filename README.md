# P4-CompilerAndLanguage

A compiler for the Flow-language which is used to implement data-flow models, and artificial neural network models in particular

## Dependencies
Add the four .jar dependencies as libraries in the project.

## Building the project
The compiler is dependent on JFlex and Cup for generating the scanner and parser based on the specification-files in JFlex/specification.jflex and Cup/specification.cup.
To allow running these an ant-script needs to be added and run prior to compilation. (Intellij allows right-clicking the compile-target -> execute on -> before compilation)

## Using the compiler
To compile a single program written in the Flow-language, insert the code in the data/input file and run the src/AutoGen/MainParse main method.
The generated java-code will appear in the src/AutoGen/CodeGen package, where the blocks can be used as classes.
An instance of a block-class can be trained with the "train"-method, and later evaluated with the "evaluateInput"-method.
An example of using a block-class can be found in src/CodeGeneration/DataFlow/Executions/MainBlock.

