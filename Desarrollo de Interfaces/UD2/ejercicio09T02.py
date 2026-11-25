import sys
from PySide6.QtWidgets import QApplication, QMainWindow, QDateEdit
from PySide6.QtCore import QDate, Slot


class VentanaPrincipal(QMainWindow):

    def __init__(self):
        super().__init__()
        self.resize(400, 100)

        # Selector de fecha con la fecha actual del sistema
        self.fecha = QDateEdit()
        self.fecha.setDate(QDate.currentDate())
        # Calendario desplegable para elegir la fecha
        self.fecha.setCalendarPopup(True)
        # Formato día/mes/año
        self.fecha.setDisplayFormat("dd/MM/yyyy")
        self.setCentralWidget(self.fecha)

        self.fecha.dateChanged.connect(self.cambiar_fecha)
        self.cambiar_fecha(self.fecha.date())

    @Slot(QDate)
    def cambiar_fecha(self, fecha):
        texto = fecha.toString("dd/MM/yyyy")
        self.setWindowTitle(f"Fecha de entrega: {texto}")
        print(f"Fecha de entrega seleccionada: {texto}")


if __name__ == "__main__":
    app = QApplication(sys.argv)

    ventana = VentanaPrincipal()
    ventana.show()

    sys.exit(app.exec())
