package me.hapyl.fight.database;

import me.hapyl.spigotutils.module.chat.Chat;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public class PlayerDatabaseEntry {

    private final PlayerDatabase playerDatabase;
    private String path;

    public PlayerDatabaseEntry(PlayerDatabase playerDatabase) {
        this.playerDatabase = playerDatabase;
        this.path = null;
    }

    /**
     * Returns the database associated with this entry.
     *
     * @return - PlayerDatabase.
     */
    public PlayerDatabase getDatabase() {
        return playerDatabase;
    }

    /**
     * Return current document aka root.
     *
     * @return - Document
     */
    public Document getDocument() {
        return playerDatabase.getDocument();
    }

    /**
     * Gets the {@link OfflinePlayer} associated with this entry.
     *
     * @return the offline player.
     */
    @Nonnull
    public OfflinePlayer getPlayer() {
        return this.playerDatabase.getPlayer();
    }

    /**
     * Gets the {@link Player} associated with this entry; or null if they're offline.
     *
     * @return the player associated with this entry; or null if they're offline.
     */
    @Nullable
    public Player getOnlinePlayer() {
        return getPlayer().getPlayer();
    }

    public void sendMessage(@Nonnull String message, @Nullable Object... format) {
        final Player player = getOnlinePlayer();

        if (player != null) {
            Chat.sendMessage(player, message, format);
        }
    }

    protected String path() {
        return this.path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the value from a document by a given string.
     * <p>
     * The string can be separated by a dot (.) to access nested documents.
     * </p>
     *
     * @param paths - Path to value.
     * @param def   - Default value.
     * @return - Value or def if not found.
     */
    protected final <T> T getValue(@Nonnull String paths, @Nullable T def) {
        return MongoUtils.get(getDocument(), paths, def);
    }

    /**
     * Sets the value from a document by a given string.
     * <p>
     * The string can be separated by a dot (.) to access nested documents.
     * If the string does not exist, it will be created.
     * </p>
     *
     * @param paths - Path to value.
     * @param value - Value to set.
     */
    protected final <T> void setValue(String paths, T value) {
        MongoUtils.set(getDocument(), paths, value);
    }

    protected final <T> void setValueIfNotSet(@Nonnull String paths, @Nonnull T value) {
        if (getValue(paths, null) != null) {
            return;
        }

        setValue(paths, value);
    }

    /**
     * Returns a a document in this (root) document.
     * <p>
     * <code>getInDocument("string");</code> will return the following:
     * </p>
     * <pre>
     *     {
     *          "string": {
     *              ...
     *          }
     *     }
     * </pre>
     *
     * @param path - Path to document.
     * @return - Document
     */
    protected Document getInDocument(String path) {
        return getDocument().get(path, new Document());
    }

    protected Document getInDocument() {
        validatePath();
        return getInDocument(path);
    }

    /**
     * Fetches a document from the root document and puts it in the root document.
     *
     * @param path     - Path to document.
     * @param consumer - Consumer to accept the document.
     */
    protected void fetchDocument(String path, Consumer<Document> consumer) {
        final Document document = getInDocument(path);

        consumer.accept(document);
        getDocument().put(path, document);
    }

    protected void fetchDocument(Consumer<Document> consumer) {
        validatePath();
        fetchDocument(path, consumer);
    }

    /**
     * Fetches a document from the root document and gets the value from it according to the function.
     *
     * @param path     - Path to document.
     * @param function - Function to get the value from the document.
     * @return - Value from the document.
     */
    protected <T> T fetchFromDocument(String path, Function<Document, T> function) {
        final Document document = getInDocument(path);

        return function.apply(document);
    }

    protected <T> T fetchFromDocument(Function<Document, T> function) {
        validatePath();
        return fetchFromDocument(path, function);
    }

    private void validatePath() {
        if (path == null || path.isEmpty() || path.isBlank()) {
            throw new IllegalArgumentException("string cannot be null or empty!");
        }
    }

}
