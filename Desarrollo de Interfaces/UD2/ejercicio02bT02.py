import sys
from PySide6.QtWidgets import QApplication, QWidget, QPushButton, QVBoxLayout

class Ventana(QWidget):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Ejercicio 02b T02")
        self.resize(300, 150)

        self.boton = QPushButton("Pulsar")
        self.boton.setCheckable(False)

        self.boton.pressed.connect(self.boton_presionado)
        self.boton.released.connect(self.boton_liberado)

        layout = QVBoxLayout()
        layout.addWidget(self.boton)
        self.setLayout(layout)


    def boton_presionado(self):
        self.boton.setText("Soltar")
        print("Botón presionado")


    def boton_liberado(self):
        self.boton.setText("Pulsar")
        print("Botón liberado")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    ventana = Ventana()
    ventana.show()
    sys.exit(app.exec())
