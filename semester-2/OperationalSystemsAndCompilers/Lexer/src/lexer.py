import re
from pprint import pprint

from lexer_token import Token
from src.finite_automaton import FiniteAutomaton


class Lexer:
    def __init__(self, text):
        self.text = text
        self.fa = FiniteAutomaton(text)
        self.remove_trailing_whitespaces()
        self.finders = [
            self.find_punctuation_marks,
            self.find_literals_and_multi_line_strings,
            self.find_comments
        ]

    def remove_trailing_whitespaces(self):
        current = 0
        for char in self.text[::-1]:
            if char == ' ' or char == '\n':
                current -= 1
            else:
                break

        self.text = self.text[:current]
        self.fa.text = self.text

    def skip_newline_chars(self):
        count = 0
        for char in self.text:
            if char == '\n':
                count += 1
            else:
                break

        self.text = self.text[count:]
        self.fa.text = self.text

    def skip_whitespaces(self):
        count = 0
        for char in self.text:
            if char == ' ':
                count += 1
            else:
                break

        extra = count % 4
        self.text = self.text[extra:]
        self.fa.text = self.text

    def get_tokens(self):
        result = []

        while len(self.text) > 0:
            token = self.get_token()
            if token:
                result.append(token)
            else:
                result.append(Token('lexical_error', self.text[0]))
                self.text = self.text[1:]
                self.fa.text = self.text

        return result

    def get_token(self):
        self.skip_whitespaces()
        self.skip_newline_chars()
        token = self.fa.get_token()
        if token:
            self.text = self.fa.text
            return token
        else:
            token = self.find_tokens()
            return token

    def find_tokens(self):
        if len(self.text) > 0:
            for finder in self.finders:
                token = finder()
                if token:
                    return token

        return None

    def find_punctuation_marks(self):
        token = self.find_brackets()
        if not token:
            if self.text[0] == '.':
                token = Token('dot')
            if self.text[0] == ',':
                token = Token('comma')
            if self.text[0] == ':':
                token = Token('colon')
            if self.text[0] == ';':
                token = Token('semicolon')
            if self.text[0] == '@':
                token = Token('decorator')

            if token:
                self.text = self.text[1:]

        return token

    def find_brackets(self):
        token = None

        if self.text[0] == '(':
            token = Token('opening_bracket')
        if self.text[0] == ')':
            token = Token('closing_bracket')
        if self.text[0] == '{':
            token = Token('opening_curly_bracket')
        if self.text[0] == '}':
            token = Token('closing_curly_bracket')
        if self.text[0] == '[':
            token = Token('opening_square_bracket')
        if self.text[0] == ']':
            token = Token('closing_square_bracket')

        if token:
            self.text = self.text[1:]

        return token

    def find_literals_and_multi_line_strings(self):
        token = None

        multi_line_string_pattern = '^(\'\'\'|\"\"\")[^\'\"]*(\'\'\'|\"\"\")'
        literal_pattern = '^(\'|\")[^\'\"]*(\'|\")'
        if re.match(multi_line_string_pattern, self.text):
            last_index = re.search(multi_line_string_pattern, self.text).span()[1]
            token = Token('multi_line_string', self.text[3:last_index - 3])
            self.text = re.sub(multi_line_string_pattern, '', self.text)
        elif re.match(literal_pattern, self.text):
            last_index = re.search(literal_pattern, self.text).span()[1]
            token = Token('literal', self.text[1:last_index - 1])
            self.text = re.sub(literal_pattern, '', self.text)

        return token

    def find_comments(self):
        token = None
        comment_pattern = '^#[^\n]*\n'

        if re.match(comment_pattern, self.text):
            last_index = re.search(comment_pattern, self.text).span()[1]
            token = Token('comment', self.text[1:last_index - 1])
            self.text = re.sub(comment_pattern, '', self.text)

        return token


if __name__ == '__main__':
    with open('src/input.txt', 'r') as f:
        text = f.read()
        lexer = Lexer(text)
        tokens = lexer.get_tokens()
        pprint(tokens)
