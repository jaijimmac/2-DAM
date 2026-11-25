
import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QSpinBox
from PySide6.QtCore import Slot

class VentanaPrincipal(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("Participantes: 15 personas")
        self.spinbox = QSpinBox()

        self.spinbox.setMinimum(1)
        self.spinbox.setMaximum(30)
        self.spinbox.setValue(15)
        self.spinbox.setSuffix(" personas")
        self.setCentralWidget(self.spinbox)
        self.spinbox.valueChanged.connect(
            self.cambiar_participantes
        )
        self.cambiar_participantes(
            self.spinbox.value()
        )

    @Slot(int)
    def cambiar_participantes(self, valor):

        self.setWindowTitle(
            f"Participantes: {valor} personas"
        )
        print(f"Número de participantes: {valor}")


if __name__ == "__main__":

    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
