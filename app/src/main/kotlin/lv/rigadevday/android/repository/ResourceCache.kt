package lv.rigadevday.android.repository

object ResourceCache {

    private val RESOURCES = mutableMapOf<String, String>()

    fun update(newMap: Map<String, String>) {
        RESOURCES.clear()
        RESOURCES.putAll(newMap)
    }

    fun get(tag: String) = RESOURCES.getOrDefault(tag, tag)

}
