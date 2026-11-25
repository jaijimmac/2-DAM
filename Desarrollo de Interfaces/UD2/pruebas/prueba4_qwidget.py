import sys
from PySide6.QtWidgets import QApplication, QWidget, QPushButton

class Ventana(QWidget):
    def __init__(self):
        super().__init__()

        #Titulo de la ventana.
        self.setWindowTitle("Autervix")
        
        #Tamaño de la ventana.
        self.resize(600, 350)
        self.setMinimumSize(400, 250)

        #Creación de botón + posición.
        self.boton = QPushButton("Login")
        self.setCentralWidget(self.boton)
        

if __name__ == "__main__":
    app = QApplication(sys.argv)
    ventana = Ventana()
    ventana.show()
    sys.exit(app.exec())
