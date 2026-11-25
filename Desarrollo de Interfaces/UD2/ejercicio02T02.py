import sys
from PySide6.QtCore import Slot
from PySide6.QtWidgets import QApplication, QMainWindow, QPushButton


class Ventana(QMainWindow):

    def __init__(self):
        super().__init__()

        # Configuración de la ventana (estado inicial: desactivado)
        self.setWindowTitle("Modo mantenimiento: DESACTIVADO")
        self.resize(400, 200)

        # Botón conmutable: alterna entre marcado y desmarcado
        self.boton = QPushButton("Activar modo mantenimiento")
        self.boton.setCheckable(True)
        self.setCentralWidget(self.boton)

        # clicked -> registra en consola las activaciones del usuario
        self.boton.clicked.connect(self.registrar_activacion)
        # toggled -> actualiza la interfaz con cualquier cambio de estado
        self.boton.toggled.connect(self.actualizar_estado)

    @Slot(bool)
    def registrar_activacion(self, marcado):
        estado = "ACTIVADO" if marcado else "DESACTIVADO"
        print(f"[clicked] El usuario ha pulsado el botón -> {estado}")

    @Slot(bool)
    def actualizar_estado(self, marcado):
        if marcado:
            self.setWindowTitle("Modo mantenimiento: ACTIVADO")
            self.boton.setText("Desactivar modo mantenimiento")
        else:
            self.setWindowTitle("Modo mantenimiento: DESACTIVADO")
            self.boton.setText("Activar modo mantenimiento")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = Ventana()
    ventana.show()

    # Prueba: cambiar el estado desde código también actualiza la interfaz
    # ventana.boton.setChecked(True)

    sys.exit(app.exec())
