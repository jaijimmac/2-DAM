import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QDial
from PySide6.QtCore import Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.resize(300, 300)

        # Control giratorio (rueda) de 0 a 100
        self.volumen = QDial()
        self.volumen.setRange(0, 100)
        self.volumen.setValue(50)
        self.volumen.setNotchesVisible(True)   # muestra las marcas
        self.setCentralWidget(self.volumen)

        self.volumen.valueChanged.connect(self.cambiar_volumen)
        self.cambiar_volumen(self.volumen.value())

    @Slot(int)
    def cambiar_volumen(self, valor):
        self.setWindowTitle(f"Volumen (QDial): {valor} %")
        print(f"Volumen: {valor} %")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
