export function media(lista) {
  let suma = 0;
  lista.forEach((alumno) => {
    suma += alumno.nota;
  });
  return suma / lista.length;
}

export function aprobados(lista) {
  return lista.filter((alumno) => alumno.nota >= 5);
}

export function mejor(lista) {
  let mejorAlumno = lista[0];
  lista.forEach((alumno) => {
    if (alumno.nota > mejorAlumno.nota) mejorAlumno = alumno;
  });
  return mejorAlumno;
}
