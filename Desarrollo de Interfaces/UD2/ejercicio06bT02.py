import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QCheckBox
from PySide6.QtCore import Qt, Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.setWindowTitle("Notificaciones desactivadas")
        self.resize(400, 120)

        self.checkbox = QCheckBox("Activar notificaciones")
        # Permite un tercer estado: parcialmente marcado
        self.checkbox.setTristate(True)
        self.checkbox.setCheckState(Qt.CheckState.Unchecked)
        self.setCentralWidget(self.checkbox)

        # checkStateChanged envía un Qt.CheckState (Qt 6.7 o superior)
        self.checkbox.checkStateChanged.connect(self.cambiar_estado)

    @Slot(Qt.CheckState)
    def cambiar_estado(self, estado):
        if estado == Qt.CheckState.Checked:
            mensaje = "Notificaciones activadas"
        elif estado == Qt.CheckState.PartiallyChecked:
            mensaje = "Solo notificaciones importantes"
        else:
            mensaje = "Notificaciones desactivadas"

        self.setWindowTitle(mensaje)
        print(f"Estado: {estado.name} -> {mensaje}")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
