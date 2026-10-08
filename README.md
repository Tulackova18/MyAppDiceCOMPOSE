Compose 

Nepracuje se přímo s konkrétním prvkem Text, ale s proměnnou představující stav aplikace, například diceValue. 
Tato proměnná je vytvořena pomocí remember a mutableStateOf. 
Samotné zobrazení kostky je potom odvozené od její aktuální hodnoty pomocí Text(text = diceSymbols[diceValue - 1]). 
Když se během animace změní hodnota diceValue, Compose změnu zaznamená a automaticky znovu vykreslí příslušnou část uživatelského rozhraní s novým symbolem. 
Zatímco tedy XML přístup říká přímo „změň text tohoto prvku“, Compose přístup říká „změnil se stav aplikace“ a podle tohoto stavu automaticky aktualizuje zobrazení
