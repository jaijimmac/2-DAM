import sys
from PySide6.QtWidgets import (QApplication, QMainWindow, QSlider,
                               QProgressBar, QWidget, QVBoxLayout)
from PySide6.QtCore import Qt


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.setWindowTitle("Simulador de progreso")
        self.resize(400, 120)

        # Deslizador que controla el avance
        self.deslizador = QSlider(Qt.Orientation.Horizontal)
        self.deslizador.setRange(0, 100)
        self.deslizador.setValue(0)

        # Barra de progreso con el mismo intervalo
        self.progreso = QProgressBar()
        self.progreso.setRange(0, 100)
        self.progreso.setValue(0)
        # %p se sustituye por el porcentaje calculado
        self.progreso.setFormat("Progreso: %p %")

        # Conexión directa: señal de un widget -> slot de otro widget
        self.deslizador.valueChanged.connect(self.progreso.setValue)

        # Colocamos los dos widgets en columna
        central = QWidget()
        layout = QVBoxLayout(central)
        layout.addWidget(self.deslizador)
        layout.addWidget(self.progreso)
        self.setCentralWidget(central)


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
