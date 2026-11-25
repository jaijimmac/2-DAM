import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QLabel
from PySide6.QtCore import Qt
from PySide6.QtGui import QFont


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Ventana de Bienvenida")

        etiqueta = QLabel("¡Bienvenido a mi aplicación!\nGracias por utilizar nuestro programa.")

        fuente = QFont()
        fuente.setPointSize(20)
        etiqueta.setFont(fuente)

        etiqueta.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignBottom)
        etiqueta.setWordWrap(True)
        self.setCentralWidget(etiqueta)

        self.resize(600, 300)


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
