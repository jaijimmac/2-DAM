
import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QTextEdit
from PySide6.QtCore import Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Editor de notas — 0 caracteres")

        self.editor = QTextEdit()

        self.editor.setPlaceholderText("Escribe aquí tus notas")

        self.editor.setAcceptRichText(False)

        self.setCentralWidget(self.editor)

        self.editor.textChanged.connect(self.actualizar_titulo)

        self.resize(600, 400)

    @Slot()
    def actualizar_titulo(self):

        texto = self.editor.toPlainText()

        caracteres = len(texto)

        palabras = len(texto.split())
        self.setWindowTitle(
            f"Editor de notas — {caracteres} caracteres — {palabras} palabras"
        )


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
