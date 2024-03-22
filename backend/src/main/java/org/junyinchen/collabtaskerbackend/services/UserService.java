package org.junyinchen.collabtaskerbackend.services;

import org.junyinchen.collabtaskerbackend.exceptions.IncorrectAgeValueException;
import org.junyinchen.collabtaskerbackend.models.Role;
import org.junyinchen.collabtaskerbackend.models.TodoBoard;
import org.junyinchen.collabtaskerbackend.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface for user service operations, providing methods for managing users, roles, and their
 * associations. It also includes methods for retrieving user-specific data like owned, member, and
 * all boards a user has access to, as well as a method to check if a user is an adult based on age.
 */
public interface UserService {
    /**
     * Saves a user entity to the database.
     *
     * @param user the user to save
     * @return the saved user
     */
    User saveUser(User user);

    /**
     * Saves a role entity to the database.
     *
     * @param role the role to save
     * @return the saved role
     */
    Role saveRole(Role role);

    /**
     * Adds a role to a user by username and roleName.
     *
     * @param username the username of the user
     * @param roleName the name of the role to add to the user
     */
    void addRoleToUser(String username, String roleName);

    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found, or an empty {@link Optional} if not
     *     found
     */
    Optional<User> getUser(String username);

    /**
     * Retrieves all boards owned by a user.
     *
     * @param username the username of the user
     * @return a collection of boards owned by the user
     */
    Collection<TodoBoard> getOwnedBoards(String username);

    /**
     * Retrieves all boards where a user is a member.
     *
     * @param username the username of the user
     * @return a collection of boards where the user is a member
     */
    Collection<TodoBoard> getMemberBoards(String username);

    /**
     * Retrieves all boards a user has access to, both owned and membership.
     *
     * @param username the username of the user
     * @return a collection of all boards the user has access to
     */
    Collection<TodoBoard> getAllBoards(String username);

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<User> getUsers();

    /**
     * Checks if a person of a given age is considered an adult.
     *
     * @param age the age to check
     * @return true if the person is an adult, false otherwise
     * @throws IncorrectAgeValueException if the age value is incorrect
     */
    boolean isAdult(int age) throws IncorrectAgeValueException;
}
