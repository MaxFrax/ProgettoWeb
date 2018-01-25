<div class="col-xs-12 col-md-8 col-md-offset-2">
    <form>
        <div class="page-header">
            <h4>Informazioni</h4>
        </div>
        <div class="row">
            <div class="form-group col-xs-12">
                <label for="name">Nome del negozio</label>
                <input type="text" class="form-control" id="name" placeholder="Aggiungi nome" value="${shop.name}">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-12">
                <label for="description">Descrizione</label>
                <textarea class="form-control" rows="4" id="description" placeholder="Aggiungi descrizione">${shop.description}</textarea>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-12">
                <label for="website">Sito web</label>
                <input type="url" class="form-control" id="website" placeholder="Aggiungi sito web">${shop.website}</textarea>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-12">
                <button class="btn btn-primary pull-right" type="submit">Salva modifiche</button>
            </div>
        </div>
    </form>

    <div class="page-header">
        <h4>Foto del negozio</h4>
    </div>
            
    <form>
        <div class="row">
            <div class="form-group col-xs-12">
                <label>Foto</label>
                <div class="dropzone panel panel-default" id="dropzone"></div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-12">
                <button class="btn btn-primary pull-right" type="submit">Aggiungi foto</button>
            </div>
        </div>
    </form>

    <div class="page-header">
        <h4>Negozio fisico</h4>
    </div>
    <p>
        Se possiedi un negozio fisico, puoi modificarne qui i dettagli. Tieni presente che le informazioni saranno visibili a tutti gli utenti.
    </p>
    <div class="row">
        <div class="form-group col-xs-12">
            <label>Cerca per indirizzo</label>
            <div class="left-inner-addon">
                <span class="glyphicon glyphicon-search"></span>
                <input type="text" name="place" class="form-control" id="place-search" placeholder="es. Via Giuseppe Garibaldi, 12, Imola, BO, Italia">
            </div>
        </div>
    </div>
    <form id="shop-address">
        <div class="row">
            <div class="form-group col-xs-9 col-sm-10">
                <label>Via</label>
                <input type="text" class="form-control" name="route" placeholder="es. Via Garibaldi">
            </div>
            <div class="form-group col-xs-3 col-sm-2 street-number">
                <label>Numero</label>
                <input type="text" class="form-control" name="street_number" placeholder="es. 12">
            </div>
        </div>
        <div class="form-group">
            <label>Città</label>
            <input type="text" class="form-control" name="administrative_area_level_3" placeholder="es. Imola">
        </div>
        <div class="row">
            <div class="form-group col-xs-8">
                <label>Provincia</label>
                <input type="text" class="form-control" name="administrative_area_level_2" placeholder="es. Bologna">
            </div>
            <div class="form-group col-xs-4 postal-code">
                <label>Codice postale</label>
                <input type="text" class="form-control" name="postal_code" placeholder="es. 40026">
            </div>
        </div>
        <input type="hidden" name="lat">
        <input type="hidden" name="lng">
        <div class="form-group">
            <button class="btn btn-primary pull-right" type="submit">Salva modifiche</button>
        </div>
    </form>


</div>
