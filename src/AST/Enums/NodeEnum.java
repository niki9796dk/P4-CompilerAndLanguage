package AST.Enums;

/**
 * An enum representing the type of a node, which is often its class.
 * Recommended use is easily channel switches with nodes, depending on their type.
 */
public enum NodeEnum {
    ASSIGN,
    BLOCK,
    BLOCK_TYPE,
    BLUEPRINT,
    BLUEPRINT_TYPE,
    BUILD,
    CHAIN,
    CHANNEL_DECLARATIONS,
    DRAW,
    GROUP,
    CHANNEL_IN_MY,
    CHANNEL_IN_TYPE,
    OPERATION_TYPE,
    CHANNEL_OUT_MY,
    CHANNEL_OUT_TYPE,
    PARAMS,
    PROCEDURE_CALL,
    PROCEDURE,
    ROOT,
    SELECTOR,
    SIZE,
    SIZE_TYPE,
    SOURCE_TYPE
}
