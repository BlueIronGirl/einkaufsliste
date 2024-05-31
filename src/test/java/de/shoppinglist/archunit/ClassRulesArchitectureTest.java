package de.shoppinglist.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.shoppinglist.controller.AuthController;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

@AnalyzeClasses(packages = "de.shoppinglist")
public class ClassRulesArchitectureTest {
    /**
     * A rule that checks that all controllers are in a controller package, are annotated with Controller or RestController and have Controller at the end of the class name.
     */
    @ArchTest
    private final ArchRule controller_should_be_annotated_with_controller_annotation_and_should_have_a_method_security_check_and_have_a_name_end_with_controller_and_are_inside_controller_package = classes()
            .that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .or().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..controller..")
            .andShould().beAnnotatedWith(Controller.class).orShould().beAnnotatedWith(RestController.class)
            .andShould().beAnnotatedWith(PreAuthorize.class).orShould().be(AuthController.class)
            .andShould().haveSimpleNameEndingWith("Controller")
            .because("controllers should be in a controller package, be annotated with Controller or RestController and have Controller at the end of the class name.");

    /**
     * A rule that checks that all services are in a service package, are annotated with Service and have Service at the end of the class name.
     */
    @ArchTest
    private final ArchRule service_should_be_annotated_and_have_a_name_end_with_service_and_are_inside_service_package = classes()
            .that().areAnnotatedWith(Service.class)
            .or().haveSimpleNameEndingWith("Service")
            .should().resideInAPackage("..service..")
            .andShould().beAnnotatedWith(Service.class)
            .andShould().haveSimpleNameEndingWith("Service")
            .because("services should be in a service package, be annotated with Service and have Service at the end of the class name.");

    /**
     * A rule that checks that all repositories are in a repository package, extend JpaRepository and have Repository at the end of the class name.
     */
    @ArchTest
    private final ArchRule repository_should_extend_jparepository_and_have_a_name_end_with_repository_and_are_inside_repository_package = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .should().resideInAPackage("..repository..")
            .andShould().beAssignableTo(JpaRepository.class)
            .andShould().haveSimpleNameEndingWith("Repository")
            .because("repositories should be in a repository package, extend JpaRepository and have Repository at the end of the class name.");

    /**
     * A rule that checks that all entities are in a entity package and are annotated with Entity.
     */
    @ArchTest
    private final ArchRule entities_should_be_annotated_and_are_inside_entity_package = classes()
            .that().areAnnotatedWith(Entity.class)
            .should().resideInAPackage("..entity..")
            .andShould().beAnnotatedWith(Entity.class)
            .because("entities should be in a entity package and be annotated with Entity.");

    /**
     * A rule that checks that no class that are outside of the config package depend on classes that are inside the config package.
     */
    @ArchTest
    private final ArchRule no_classes_should_depend_on_config_classes = noClasses()
            .that().resideOutsideOfPackage("..config")
            .should().dependOnClassesThat().resideInAPackage("..config..");

    /**
     * A rule that checks that no class depends on upper packages
     */
    @ArchTest
    private final ArchRule no_classes_should_depend_upper_packages = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    /**
     * A rule that checks that no class depends on deprecated classes
     */
    @ArchTest
    private final ArchRule no_classes_should_depend_on_deprecated_classes = noClasses().should().dependOnClassesThat().areAnnotatedWith(Deprecated.class);


}
