/* (C)2024 */
package CBuilder.objects.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import CBuilder.Expression;
import CBuilder.Statement;
import CBuilder.literals.IntLiteral;
import CBuilder.objects.AttributeReference;
import CBuilder.objects.Call;
import CBuilder.variables.VariableDeclaration;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestFunction {
    String testClass = "[FUNCTION]\n";

    @ParameterizedTest
    @MethodSource("sources_build_cfunction")
    void build_cfunction(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.buildCFunction());

        assertEquals(expected, function.buildCFunction());
    }

    @ParameterizedTest
    @MethodSource("sources_build_func_object_declaration")
    void build_func_object_declaration(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.buildFuncObjectDeclaration());

        assertEquals(expected, function.buildFuncObjectDeclaration());
    }

    @ParameterizedTest
    @MethodSource("sources_build_initialisation")
    void build_initialisation(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.buildInitialisation());

        assertEquals(expected, function.buildInitialisation());
    }

    @ParameterizedTest
    @MethodSource("sources_build_refdec")
    void build_refdec(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.buildRefDec());

        assertEquals(expected, function.buildRefDec());
    }

    @ParameterizedTest
    @MethodSource("sources_build_expression")
    void build_expression(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.buildExpression());

        assertEquals(expected, function.buildExpression());
    }

    @ParameterizedTest
    @MethodSource("sources_get_name")
    void get_name(
            String funcName,
            List<Statement> body,
            List<Argument> positionalArgs,
            List<VariableDeclaration> localVariables,
            String expected) {
        Function function = new Function(funcName, body, positionalArgs, localVariables);

        System.out.println(testClass + function.getName());

        assertEquals(expected, function.getName());
    }

    private static Stream<Arguments> sources_build_cfunction() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "__MPyObj* func_funcName1(__MPyObj *args, __MPyObj *kwargs) {\n"
                            + "\tassert(args != NULL && kwargs != NULL);\n"
                            + "\t\n"
                            + "\t__MPyGetArgsState argHelper = __mpy_args_init(\"funcName1\", args,"
                            + " kwargs, 2);\n"
                            + "\t__MPyObj *argName1 = __mpy_args_get_positional(&argHelper, 0,"
                            + " \"argName1\");\n"
                            + "\t__MPyObj *argName2 = __mpy_args_get_positional(&argHelper, 1,"
                            + " \"argName2\");\n"
                            + "\t__mpy_args_finish(&argHelper);\n"
                            + "\t\n"
                            + "\t__MPyObj *retValue = NULL;\n"
                            + "\t\n"
                            + "\t__MPyObj *local1 = __mpy_obj_init_object();\n"
                            + "\t__mpy_obj_ref_inc(local1);\n"
                            + "\t__MPyObj *local2 = __mpy_obj_init_object();\n"
                            + "\t__mpy_obj_ref_inc(local2);\n"
                            + "\t__mpy_obj_ref_dec(__mpy_call(__mpy_obj_get_attr(__mpy_obj_init_int(1),"
                            + " \"__add__\"), __mpy_tuple_assign(0, __mpy_obj_init_int(3),"
                            + " __mpy_obj_init_tuple(1)), NULL));\n"
                            + "\t\n"
                            + "\t__mpy_obj_ref_dec(argName1);\n"
                            + "\t__mpy_obj_ref_dec(argName2);\n"
                            + "\t\n"
                            + "\tgoto ret;\n"
                            + "\tret:\n"
                            + "\tif (retValue == NULL) {\n"
                            + "\t\tretValue = __mpy_obj_init_object();\n"
                            + "\t}\n"
                            + "\treturn __mpy_obj_return(retValue);\n"
                            + "}\n"));
    }

    private static Stream<Arguments> sources_build_func_object_declaration() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "__MPyObj *funcName1;\n"));
    }

    private static Stream<Arguments> sources_build_initialisation() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "funcName1 = __mpy_obj_init_func(&func_funcName1);\n"
                                + "__mpy_obj_ref_inc(funcName1);\n"));
    }

    private static Stream<Arguments> sources_build_refdec() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "__mpy_obj_ref_dec(funcName1);\n"));
    }

    private static Stream<Arguments> sources_build_expression() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "funcName1"));
    }

    private static Stream<Arguments> sources_get_name() {
        return Stream.of(
                Arguments.of(
                        "funcName1",
                        List.of(
                                new Call(
                                        new AttributeReference("__add__", new IntLiteral(1)),
                                        List.of(new Expression[] {new IntLiteral(3)}))),
                        List.of(new Argument("argName1", 0), new Argument("argName2", 1)),
                        List.of(
                                new VariableDeclaration("local1"),
                                new VariableDeclaration("local2")),
                        "funcName1"));
    }
}
