import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QCheckBox
from PySide6.QtCore import Slot

class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.setWindowTitle("Notificaciones desactivadas")
        self.checkbox = QCheckBox("Activar notificaciones")
        self.checkbox.setChecked(False)
        self.setCentralWidget(self.checkbox)
        self.checkbox.toggled.connect(self.cambiar_estado)

    @Slot(bool)
    def cambiar_estado(self, estado):
        if estado:
            self.setWindowTitle("Notificaciones activadas")
            print("Notificaciones activadas")
        else:
            self.setWindowTitle("Notificaciones desactivadas")
            print("Notificaciones desactivadas")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
