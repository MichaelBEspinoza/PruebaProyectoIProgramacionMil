package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain;

import java.time.LocalDate;
import java.util.Random;

public class DesignPattern {

    // Variables.
    private String name, description, type, codeExamples, id, context, problem, solution;
    private LocalDate fechaAgregado;

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    // Constructores.
    public DesignPattern(String id, String name, String description, String type, String codeExamples, String context, String problem, String solution, LocalDate fechaAgregado) {
        /**
         * Clase que sirve de molde para todos los patrones de diseño a insertar.
         * @param name Indica el nombre del patrón de diseño.
         * @param description Da una breve descripción del modelo.
         * @param type Define el tipo: creacional, estructural, de comportamiento.
         * @param codeExamples Da un ejemplo corto del patrón de diseño.
         * @param patternCode Código generado aleatoriamente, asignado a un patrón como identificación.
         */
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.codeExamples = codeExamples;
        this.fechaAgregado = fechaAgregado;
        this.context = context;
        this.problem = problem;
        this.solution = solution;
    }// Constructor #1.

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    // Setters & getters.
    public DesignPattern() {
    }// Constructor #2.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeExamples() {
        return codeExamples;
    }

    public void setCodeExamples(String codeExamples) {
        this.codeExamples = codeExamples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDate fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    // Métodos de utilidad.
    // Método para generar el código del patrón aleatoriamente.
    public String generatePatternCode() {
        // Define los caracteres que pueden estar en el código.
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        // Genera un código de longitud 6.
        for (int i = 0; i < 6; i++) code.append(caracteres.charAt(rnd.nextInt(caracteres.length())));

        return code.toString();
    }// End of method [generatePatternCode].

    @Override
    public String toString() {
        return "Patrón de diseño encontrado.\nNombre: \n   "
                + name +
                "\nID: \n   "
                + id +
                "\nTipo: \n   "
                + type +
                "\nDescripción: \n   "
                + description +
                "\nProblema al que responde: \n   "
                + problem +
                "\nSolución que aporta: \n   "
                + solution +
                "\nEjemplo del patrón codificado: \n   "
                + codeExamples +
                "\nFecha de registro: \n   "
                + fechaAgregado;
    }// End of method [toString].
}// End of class [DesignPattern].

