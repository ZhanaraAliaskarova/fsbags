package com.fourseasonsweb.fsbags.data

class Product(_id: Int, _name: String, _description: String, _image: Int, _price: Double) {
    private var id: Int = 0
    private var name: String = ""
    private var description: String = ""
    private var price: Double = 0.0
    private var image: Int = 0

    init {
        id = _id
        name = _name
        description = _description
        image = _image
        price = _price
    }

    fun getId() : Int {
        return id
    }

    fun setId(value: Int) {
        id = value
    }

    fun getName() : String {
        return name
    }

    fun setName(value: String) {
        name = value
    }

    fun getDescription() : String {
        return description
    }

    fun setDescription(value: String) {
        description = value
    }

    fun getPrice() : Double {
        return price
    }

    fun setPrice(value: Double) {
        price = value
    }

    fun getImage() : Int {
        return image
    }

    fun setImage(value: Int) {
        image = value
    }
}