import sys
from PySide6.QtCore import Slot
from PySide6.QtWidgets import QApplication, QMainWindow, QPushButton

class Ventana(QMainWindow): 

    def __init__(self):
        super().__init__() 

        self.setWindowTitle("Ejercicio 2a")
        self.resize(400, 200)

        self.boton = QPushButton("Pulsar")
        self.setCentralWidget(self.boton)
        self.boton.setCheckable(True)

        self.boton.clicked.connect(self.registrar_clic)
        self.boton.toggled.connect(self.cambiarTitulo)

    @Slot(bool)
    def registrar_clic(self, marcado):
        print(f"Estado del botón: {marcado}")

    @Slot()
    def cambiarTitulo(self):
        self.setWindowTitle("Cambio de nombre a la ventana.")

   


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = Ventana()
    ventana.show()

    sys.exit(app.exec())