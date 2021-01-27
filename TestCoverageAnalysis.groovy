def ansiCyan = "\u001B[36m"
def ansiBlue = "\u001B[34m"
def ansiYellow = "\u001B[33m"
def ansiReset = "\u001B[0m"

def reportFile = new File("target/site/jacoco/index.html")

println "[" + ansiBlue + "INFO" + ansiReset + "] ------------------------------------------------------------------------"
println "[" + ansiBlue + "INFO" + ansiReset + "] " + ansiCyan + "TEST COVERAGE ANALYSIS" + ansiReset
println "[" + ansiBlue + "INFO" + ansiReset + "] ------------------------------------------------------------------------"

if (!reportFile.exists() || !reportFile.canRead()) {
    println "[" + ansiYellow + "WARN" + ansiReset + "] Unable to find jacoco report file in ./target/site/jacoco/index.html"
    return
}

reportFile.withReader('UTF-8') { reader ->
    def html = parser().parseText(reader.readLine())
    def totalRow = html.body.table.tfoot.tr
    def instructionsMissed = totalRow.td[1]
    def instructionsCoverage = totalRow.td[2]
    def branchesMissed = totalRow.td[3]
    def branchesCoverage = totalRow.td[4]

    println "[" + ansiBlue + "INFO" + ansiReset + "] Instructions  ${instructionsCoverage}  (Missed ${instructionsMissed})"
    println "[" + ansiBlue + "INFO" + ansiReset + "] Branches      ${branchesCoverage}  (Missed ${branchesMissed})"
    println "[" + ansiBlue + "INFO" + ansiReset + "]"
    println "[" + ansiBlue + "INFO" + ansiReset + "] For more information: open ./target/site/jacoco/index.html"
}

XmlSlurper parser() {
    parser = new XmlSlurper()
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    return parser
}