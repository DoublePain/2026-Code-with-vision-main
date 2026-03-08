package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSpinSubsystem;
import static edu.wpi.first.units.Units.Degrees;

public class DeployAndSpinIntake extends ParallelCommandGroup {

    public DeployAndSpinIntake(IntakeSubsystem intake, IntakeSpinSubsystem intakeSpin) {

        // Sequential command to safely deploy the intake arm
        SequentialCommandGroup deployArmSequence = new SequentialCommandGroup(
            new InstantCommand(() -> intake.setAngle(Degrees.of(170))), // deploy position
            new InstantCommand(() -> intake.setAngle(Degrees.of(180))) // optional fine adjustment
        );

        // Command to run the intake motor while the arm is deployed
   Command runIntakeSpin = new InstantCommand(() -> intakeSpin.runIntakeCommand(1.0), intakeSpin)
    .finallyDo(interrupted -> intakeSpin.stopIntakeCommand()); // stop motor if interrupted
        // Add to parallel group
        addCommands(
            deployArmSequence, // only requires IntakeSubsystem
            runIntakeSpin      // only requires IntakeSpinSubsystem
        );
    }
}