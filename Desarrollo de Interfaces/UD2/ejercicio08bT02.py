import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QDoubleSpinBox
from PySide6.QtCore import Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.resize(400, 100)

        # Control numérico decimal para el precio de inscripción
        self.precio = QDoubleSpinBox()
        self.precio.setRange(0, 500)
        self.precio.setDecimals(2)
        self.precio.setSingleStep(5)      # cada flecha suma o resta 5 €
        self.precio.setValue(50)
        self.precio.setSuffix(" €")
        self.setCentralWidget(self.precio)

        self.precio.valueChanged.connect(self.cambiar_precio)
        self.cambiar_precio(self.precio.value())

    @Slot(float)
    def cambiar_precio(self, valor):
        self.setWindowTitle(f"Precio de inscripción: {valor:.2f} €")
        print(f"Precio de inscripción: {valor:.2f} €")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
