package com.example.repaircalculator.viewHolder;

import android.content.res.Resources;
import android.text.format.DateFormat;

import androidx.recyclerview.widget.RecyclerView;

import com.example.repaircalculator.R;
import com.example.repaircalculator.data.entity.Project;
import com.example.repaircalculator.databinding.ProjectElementBinding;

import org.jetbrains.annotations.NotNull;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    private final ProjectElementBinding binding;

    public ProjectViewHolder(ProjectElementBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NotNull Project project) {


        binding.projectNameTv.setText(project.getName());
        binding.dateProjectTv.setText("sdf");
    }
}
