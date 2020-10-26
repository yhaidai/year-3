class Token:
    KEYWORDS = [
        'and',
        'as',
        'assert',
        'break',
        'class',
        'continue',
        'def',
        'del',
        'elif',
        'else',
        'except',
        'False',
        'finally',
        'for',
        'from',
        'global',
        'if',
        'import',
        'in',
        'is',
        'lambda',
        'None',
        'nonlocal',
        'not',
        'or',
        'pass',
        'raise',
        'return',
        'True',
        'try',
        'while',
        'with',
        'yield',
        # data types
        'str',
        'int',
        'float',
        'complex',
        'list',
        'tuple',
        'range',
        'dict',
        'set',
        'frozenset',
        'bool',
        'bytes',
        'bytearray',
        'memoryview',
    ]

    NAMES = KEYWORDS + [
        'number',
        'id',
        'literal',  # ' "
        'multi_line_string'  # ''' """
        'comment',
        'id',
        # operators
        'comparison_operator',  # ==, !=, >, >=, <, <=
        'assignment_operator',  # =, +=, -=, *=, /=, %=, **=, //=, &=, |=, ^=, >>=, <<=
        'arithmetic_operator',  # +, -, *, /, %, **, //
        'bitwise_operator',  # &, |, ^, ~, <<, >>
        # punctuation marks
        'comma',
        'dot',
        'colon',
        'semicolon',
        'opening_bracket',
        'closing_bracket',
        'opening_curly_bracket',
        'closing_curly_bracket',
        'opening_square_bracket',
        'closing_square_bracket',
        'indent',
        'decorator',
        # error token
        'lexical_error'
    ]

    token_table = {keyword: keyword for keyword in KEYWORDS}

    def __init__(self, name, attributes=None):
        self.name = name
        self.attributes = attributes
        self.token_table[self.attributes] = self.name

    def __repr__(self):
        return self.name + ': ' + str(self.attributes)
