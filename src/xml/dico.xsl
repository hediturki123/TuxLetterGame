<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tux="http://myGame/tux"
    version="1.0"
>
    <!-- L'output ci-desssous permet de simuler un fichier HTML5. -->
    <xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#013;</xsl:text>
        <html lang="fr">
            <head>
                <meta charset="UTF-8"/>
                <title>Tux Lettergame • Dictionnaire</title>
                <link rel="stylesheet" href="../css/main.css"/>
                <link rel="stylesheet" href="../css/dico.css"/>
            </head>
            <body>
                <h1>Tux Lettergame • Dictionnaire</h1>
                <hr/>
                <div class="niveaux">
                <!--
                    On effectue un tri par niveau de difficulté en premier lieu.
                    La sélection ci-dessous permet de récupérer le nombre de niveaux uniques (à travers un mot).
                -->
                <xsl:for-each select="//tux:mot[not(following-sibling::tux:mot/@niveau = @niveau)]">
                    <xsl:sort select="@niveau"/>
                    <xsl:call-template name="niveau">
                        <xsl:with-param name="numero" select="@niveau"/>
                    </xsl:call-template>
                </xsl:for-each>
                </div>
            </body>
        </html>
    </xsl:template>

    <!--
        Template nous permettant de générer chaque section correspondant à un niveau.
        On passe en paramètre le numéro du niveau correspondant.
    -->
    <xsl:template name="niveau">
        <xsl:param name="numero"/>
        <section class="niveau_{$numero}">
            <h2>Niveau <xsl:value-of select="$numero"/></h2>
            <!-- On présente les mots de chaque niveau sous forme de liste. -->
            <ul>
                <xsl:apply-templates select="//tux:mot[@niveau=$numero]">
                    <xsl:sort select="." order="ascending"/>
                </xsl:apply-templates>
            </ul>
        </section>
    </xsl:template>

    <!-- Chaque mot est un élément d'une liste. -->
    <xsl:template match="tux:mot">
        <li><xsl:value-of select="."/></li>
    </xsl:template>

    <!--
        Cette petite template permet d'éviter la génération d'espaces "sauvages" dans le document HTML.
        On devra en revanche récupérer nous-mêmes les noeuds textes dont on a besoin par la suite.
    -->
    <xsl:template match="text()"/>

</xsl:stylesheet>