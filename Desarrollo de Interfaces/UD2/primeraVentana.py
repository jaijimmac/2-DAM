import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QPushButton


class Ventana(QMainWindow):
    def __init__(self):
        super().__init__()

        # Configuramos la ventana
        self.setWindowTitle("Mi primera interfaz")
        self.resize(1024, 720)
        self.setMinimumSize(1024, 720)

        # Creamos un botón
        self.boton = QPushButton("Preparar mi aplicación")

        # Colocamos el botón en el centro de la ventana
        self.setCentralWidget(self.boton)


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = Ventana()
    ventana.show()

    sys.exit(app.exec())