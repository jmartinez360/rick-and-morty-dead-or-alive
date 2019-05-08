package com.dev.rickandmortydeadoralive.utils.cardColors

import java.util.*

enum class CardType (val cardColor: CardColor){

   // BASIC_COLOR(BasicCardColor())
    GRADIENT_CARD(GradientCardColor(Arrays.asList("#40E0D0", "#FF8C00", "#FF0080"), Arrays.asList("#fceabb", "#f8b500"), "#fceabb", "#ffffff", "#000000"))
}