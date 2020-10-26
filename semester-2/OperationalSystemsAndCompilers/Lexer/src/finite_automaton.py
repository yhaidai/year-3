import re

from src.lexer_token import Token


class FiniteAutomaton:
    def __init__(self, text):
        self.state = 0
        self.text = text
        self.finders = [
            self.find_id_and_keywords,
            self.find_comparison_operator,
            self.find_indent,
            self.find_number,
            self.find_other_operators,
        ]

    def get_token(self):
        for finder in self.finders:
            token = finder()
            if token is not None:
                return token

        return None

    def find_id_and_keywords(self):
        lexeme = ''
        char = ''

        for char in self.text:
            if self.state == 0:
                if re.match('[a-zA-Z_]', char):
                    self.state = 1
                    lexeme += char
                else:
                    return None
            elif self.state == 1:
                if re.match('[^a-zA-Z_0-9]', char) or len(lexeme) == len(self.text):
                    self.state = 0
                    self.text = self.text[len(lexeme):]
                    if lexeme in Token.token_table and Token.token_table[lexeme] != 'id':
                        return Token(Token.token_table[lexeme])
                    else:
                        return Token('id', lexeme)
                else:
                    lexeme += char

        if self.state == 1:
            if re.match('[^a-zA-Z_0-9]', char) or len(lexeme) == len(self.text):
                self.state = 0
                self.text = self.text[len(lexeme):]
                if lexeme in Token.token_table and Token.token_table[lexeme] != 'id':
                    return Token(Token.token_table[lexeme])
                else:
                    return Token('id', lexeme)
            else:
                lexeme += char

        return None

    def find_number(self):
        lexeme = ''
        char = ''

        for char in self.text:
            if self.state == 0:
                if re.match('[0-9]', char):
                    lexeme += char
                    self.state = 1
                else:
                    return None
            elif self.state == 1:
                if re.match('[^0-9]', char) or len(lexeme) == len(self.text):
                    self.state = 0
                    self.text = self.text[len(lexeme):]
                    return Token('number', lexeme)
                else:
                    lexeme += char

        if self.state == 1:
            if re.match('[^0-9]', char) or len(lexeme) == len(self.text):
                self.state = 0
                self.text = self.text[len(lexeme):]
                return Token('number', lexeme)
            else:
                lexeme += char

        return None

    def find_indent(self):
        lexeme = ''
        char = ''

        for char in self.text:
            if self.state == 0:
                if re.match('[ ]', char):
                    self.state = 1
                    lexeme += char
                else:
                    return None
            elif self.state == 1 or self.state == 2 or self.state == 3:
                if re.match('[^ ]', char) or len(lexeme) == len(self.text):
                    self.state = 0
                    return None
                else:
                    lexeme += char
                    self.state += 1
            elif self.state == 4:
                self.state = 0
                self.text = self.text[len(lexeme):]
                return Token('indent')

        if self.state == 1 or self.state == 2 or self.state == 3:
            if re.match('[^ ]', char) or len(lexeme) == len(self.text):
                self.state = 0
                return None
            else:
                lexeme += char
                self.state += 1
        elif self.state == 4:
            self.state = 0
            self.text = self.text[len(lexeme):]
            return Token('indent')

        return None

    def find_comparison_operator(self):
        lexeme = ''
        char = ''

        for char in self.text:
            if self.state == 0:
                if char == '>':
                    lexeme += char
                    self.state = 1
                elif char == '<':
                    lexeme += char
                    self.state = 2
                elif char == '=':
                    lexeme += char
                    self.state = 3
                else:
                    return None
            elif self.state == 1 or self.state == 2:
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    self.state = 0
                    self.text = self.text[len(lexeme):]
                    return Token('comparison_operator', lexeme)
                elif re.match('[<>]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    self.state = 4
                else:
                    self.state = 0
                    self.text = self.text[len(lexeme):]
                    return Token('comparison_operator', lexeme)
            elif self.state == 3:
                token = Token('assignment_operator', lexeme)
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    token = Token('comparison_operator', lexeme)
                self.state = 0
                self.text = self.text[len(lexeme):]
                return token
            elif self.state == 4:
                token = Token('bitwise_operator', lexeme)
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    token = Token('assignment_operator', lexeme)
                self.state = 0
                self.text = self.text[len(lexeme):]
                return token

        if self.state == 1 or self.state == 2:
            if re.match('[=]', char) and len(lexeme) != len(self.text):
                lexeme += char
                self.state = 0
                self.text = self.text[len(lexeme):]
                return Token('comparison_operator', lexeme)
            elif re.match('[<>]', char) and len(lexeme) != len(self.text):
                lexeme += char
                self.state = 4
            else:
                self.state = 0
                self.text = self.text[len(lexeme):]
                return Token('comparison_operator', lexeme)
        elif self.state == 3:
            token = Token('assignment_operator', lexeme)
            if re.match('[=]', char) and len(lexeme) != len(self.text):
                lexeme += char
                token = Token('comparison_operator', lexeme)
            self.state = 0
            self.text = self.text[len(lexeme):]
            return token
        elif self.state == 4:
            token = Token('bitwise_operator', lexeme)
            if re.match('[=]', char) and len(lexeme) != len(self.text):
                lexeme += char
                token = Token('assignment_operator', lexeme)
            self.state = 0
            self.text = self.text[len(lexeme):]
            return token

        return None

    def find_other_operators(self):
        lexeme = ''
        char = ''

        for char in self.text:
            if self.state == 0:
                if re.match('[*/]', char):
                    lexeme += char
                    self.state = 1
                elif re.match('[-+%]', char):
                    lexeme += char
                    self.state = 2
                elif re.match('[&|^]', char):
                    lexeme += char
                    self.state = 3
                elif char == '~':
                    lexeme += char
                    self.text = self.text[len(lexeme):]
                    return Token('bitwise_operator', lexeme)
                else:
                    return None
            elif self.state == 1:
                if re.match('[*/]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    self.state = 2
                else:
                    token = Token('arithmetic_operator', lexeme)
                    if re.match('[=]', char) and len(lexeme) != len(self.text):
                        lexeme += char
                        token = Token('assignment_operator', lexeme)
                    self.state = 0
                    self.text = self.text[len(lexeme):]
                    return token
            elif self.state == 2:
                token = Token('arithmetic_operator', lexeme)
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    token = Token('assignment_operator', lexeme)
                self.state = 0
                self.text = self.text[len(lexeme):]
                return token
            elif self.state == 3:
                token = Token('bitwise_operator', lexeme)
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    token = Token('assignment_operator', lexeme)
                self.state = 0
                self.text = self.text[len(lexeme):]
                return token

        if self.state == 1:
            if re.match('[*/]', char) and len(lexeme) != len(self.text):
                lexeme += char
                self.state = 2
            else:
                token = Token('arithmetic_operator', lexeme)
                if re.match('[=]', char) and len(lexeme) != len(self.text):
                    lexeme += char
                    token = Token('assignment_operator', lexeme)
                self.state = 0
                self.text = self.text[len(lexeme):]
                return token
        elif self.state == 2:
            token = Token('arithmetic_operator', lexeme)
            if re.match('[=]', char) and len(lexeme) != len(self.text):
                lexeme += char
                token = Token('assignment_operator', lexeme)
            self.state = 0
            self.text = self.text[len(lexeme):]
            return token
        elif self.state == 3:
            token = Token('bitwise_operator', lexeme)
            if re.match('[=]', char) and len(lexeme) != len(self.text):
                lexeme += char
                token = Token('assignment_operator', lexeme)
            self.state = 0
            self.text = self.text[len(lexeme):]
            return token

        return None
