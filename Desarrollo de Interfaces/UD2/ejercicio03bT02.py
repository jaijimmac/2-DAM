import sys
from pathlib import Path
from PySide6.QtWidgets import QApplication, QMainWindow, QLabel
from PySide6.QtCore import Qt
from PySide6.QtGui import QPixmap


# Carpeta donde está este archivo .py (no depende de desde dónde se ejecute)
CARPETA_BASE = Path(__file__).resolve().parent
RUTA_IMAGEN = CARPETA_BASE / "recursos" / "logo.png"


class VentanaImagen(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Ventana de Bienvenida (imagen)")
        self.resize(600, 400)

        etiqueta = QLabel()
        etiqueta.setAlignment(Qt.AlignmentFlag.AlignCenter)

        imagen = QPixmap(str(RUTA_IMAGEN))

        # Comprobamos que la imagen se ha cargado
        if imagen.isNull():
            print(f"No se ha podido cargar la imagen: {RUTA_IMAGEN}")
            etiqueta.setText("Imagen no encontrada")
        else:
            print(f"Imagen cargada correctamente: {RUTA_IMAGEN.name} "
                  f"({imagen.width()}x{imagen.height()})")
            # Escalamos manteniendo las proporciones originales
            imagen = imagen.scaled(
                500, 300,
                Qt.AspectRatioMode.KeepAspectRatio,
                Qt.TransformationMode.SmoothTransformation
            )
            etiqueta.setPixmap(imagen)

        self.setCentralWidget(etiqueta)


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaImagen()
    ventana.show()

    sys.exit(app.exec())
