import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QComboBox
from PySide6.QtCore import Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()

        self.setWindowTitle("Selector de tecnologías")
        self.resize(400, 100)

        self.combo = QComboBox()
        self.combo.addItem("Python", "PY")
        self.combo.addItem("Java", "JV")
        self.combo.addItem("JavaScript", "JS")
        self.combo.addItem("C++", "CPP")
        self.combo.addItem("C#", "CS")
        self.combo.addItem("PHP", "PHP")

        # Desplegable editable: al pulsar Intro se añade lo escrito
        self.combo.setEditable(True)
        # Las nuevas tecnologías se insertan en orden alfabético
        self.combo.setInsertPolicy(QComboBox.InsertPolicy.InsertAlphabetically)

        self.setCentralWidget(self.combo)

        self.combo.currentIndexChanged.connect(self.cambiar_tecnologia)
        self.cambiar_tecnologia(self.combo.currentIndex())

    @Slot(int)
    def cambiar_tecnologia(self, indice):
        # En un combo editable, currentText() devuelve lo escrito en la caja,
        # así que leemos el texto y el dato del elemento que indica el índice
        tecnologia = self.combo.itemText(indice)
        identificador = self.combo.itemData(indice)

        # Las opciones añadidas por el usuario no tienen identificador
        if identificador is None:
            identificador = "(nueva, sin identificador)"

        self.setWindowTitle(f"Tecnología seleccionada: {tecnologia}")
        print(f"Índice: {indice} | Tecnología: {tecnologia} | Identificador: {identificador}")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
