import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QSlider
from PySide6.QtCore import Qt, Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.resize(400, 100)

        # Deslizador horizontal de 0 a 100
        self.volumen = QSlider(Qt.Orientation.Horizontal)
        self.volumen.setRange(0, 100)
        self.volumen.setValue(50)
        self.setCentralWidget(self.volumen)

        self.volumen.valueChanged.connect(self.cambiar_volumen)
        # Mostramos el valor inicial en el título desde el inicio
        self.cambiar_volumen(self.volumen.value())

    @Slot(int)
    def cambiar_volumen(self, valor):
        self.setWindowTitle(f"Volumen: {valor} %")
        print(f"Volumen: {valor} %")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
