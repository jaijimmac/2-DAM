import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QLineEdit
from PySide6.QtCore import Slot

class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Ejercicio 4")

        self.campo = QLineEdit()

        self.campo.setPlaceholderText("Introduce el nombre del proyecto")
        self.campo.setMaxLength(30)
        self.setCentralWidget(self.campo)

        self.campo.textChanged.connect(self.actualizar_titulo)

        self.resize(500, 150)

    @Slot(str)
    def actualizar_titulo(self, texto):

        if texto.strip() == "":
            self.setWindowTitle("Proyecto sin nombre")
        else:
            self.setWindowTitle(texto.upper())


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
