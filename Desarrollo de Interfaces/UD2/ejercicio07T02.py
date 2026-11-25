
import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QComboBox
from PySide6.QtCore import Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Selector de tecnologías")

        self.combo = QComboBox()
        self.combo.addItem("Python", "PY")
        self.combo.addItem("Java", "JV")
        self.combo.addItem("JavaScript", "JS")
        self.combo.addItem("C++", "CPP")
        self.combo.addItem("C#", "CS")
        self.combo.addItem("PHP", "PHP")

        self.setCentralWidget(self.combo)

        self.combo.currentIndexChanged.connect(
            self.cambiar_tecnologia
        )

        self.cambiar_tecnologia(self.combo.currentIndex())

    @Slot(int)
    def cambiar_tecnologia(self, indice):

        tecnologia = self.combo.currentText()
        identificador = self.combo.currentData()

        self.setWindowTitle(
            f"Tecnología seleccionada: {tecnologia}"
        )

        print(f"Índice: {indice}")
        print(f"Tecnología: {tecnologia}")
        print(f"Identificador: {identificador}")
        print("------------------------")


if __name__ == "__main__":

    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
