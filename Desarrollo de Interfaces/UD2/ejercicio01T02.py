import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QPushButton

class Ventana(QMainWindow):
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
        
class VentanaAlternativa(QMainWindow):
    def __init__(self):
        super().__init__()
        #Titulo de la ventana.
        self.setWindowTitle("Mi App")
        
        #Tamaño de la ventana.
        self.resize(500, 300)
        self.setMinimumSize(350, 200)
        
        #Creación de botón + posición.
        self.boton = QPushButton("Crear Cuenta")
        self.setCentralWidget(self.boton)


if __name__ == "__main__":
    app = QApplication(sys.argv)

    #Se crea la ventana y se muestra.
    ventana = Ventana()
    ventana2 = VentanaAlternativa()
    
    seleccion = input("Que ventana quieres enseñar 1/2\n")
    
    if seleccion == "1":
        ventana.show()
    elif seleccion == "2": 
        ventana2.show()
    
    sys.exit(app.exec())
    
    
    
